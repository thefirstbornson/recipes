package ru.otus.recipes.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@Aspect
@Component
public class CompactJsonAspect {

    private final ObjectMapper objectMapper;

    public CompactJsonAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(ru.otus.recipes.service.mapper.CompactJson)")
    public ResponseEntity<?> makeCompact(ProceedingJoinPoint pjp) throws Throwable {
        Method method = getMethodFromJoinPoint(pjp);
        List<String> parametersNames = getParameterNames(method);
        CompactJson compactJsonAnnotation = method.getAnnotation(CompactJson.class);

        String expansionsParamName = compactJsonAnnotation.expansions();
        String includingsParamName = compactJsonAnnotation.includings();
        Object[] methodArguments = pjp.getArgs();

        List<String> expansionsList = getArgumentList(parametersNames.indexOf(expansionsParamName),methodArguments);
        List<String> includingsList = getArgumentList(parametersNames.indexOf(includingsParamName),methodArguments);

        CompressionCriteria compressionCriteria = CompressionCriteria.builder()
                .includingsList(includingsList)
                .expansionsList(expansionsList)
                .build();

        ResponseEntity<?> methodResult = (ResponseEntity<?>) pjp.proceed();
        JsonNode jsonNodeToCompress = objectMapper.valueToTree(methodResult.getBody());
        compressJsonNode(jsonNodeToCompress, compressionCriteria);

//        methodResult = ResponseEntity.ok(jsonNodeToCompress);
        return ResponseEntity.ok(jsonNodeToCompress);
    }

    void compressJsonNode(final JsonNode node, CompressionCriteria compressionCriteria) {
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            final String key = field.getKey();
            final JsonNode value = field.getValue();

            if (value.isObject()) {
                increasePath(compressionCriteria, key);
                ObjectNode objectNode = (ObjectNode) value;
                if (!compressionCriteria.getExpansionsList().contains(compressionCriteria.getPath().toString()) &
                        compressionCriteria.getExpansionsList().stream().noneMatch(expansion -> expansion.contains(compressionCriteria.getPath().toString() + ".")) &
                        !compressionCriteria.getIncludingsList().contains(compressionCriteria.getPath().toString())) {
                    objectNode.retain("id");
                } else {
                    compressJsonNode(objectNode,compressionCriteria);
                }
                reducePath(compressionCriteria);
            } else if (value.isArray()) {
                increasePath(compressionCriteria, key);
                ArrayNode arrayNode = (ArrayNode) value;
                if (!compressionCriteria.getIncludingsList().contains(compressionCriteria.getPath().toString()) &
                        compressionCriteria.getIncludingsList().stream().noneMatch(including -> including.contains(compressionCriteria.getPath().toString() + "."))) {
                    arrayNode.removeAll();
                } else {
                    arrayNode.forEach(jsonNode -> {compressJsonNode(jsonNode, compressionCriteria);});
                }
                reducePath(compressionCriteria);
            }
        }
    }

    private void reducePath(CompressionCriteria compressionCriteria) {
        if (compressionCriteria.getPath().lastIndexOf(".") > 0) {
            compressionCriteria.getPath().replace(compressionCriteria.getPath().lastIndexOf("."),
                    compressionCriteria.getPath().length(), "");
        } else if (compressionCriteria.getPath().length() > 0) {
            compressionCriteria.getPath().setLength(0);
        }
    }

    private void increasePath(CompressionCriteria compressionCriteria, String path) {
        if (compressionCriteria.getPath().length() == 0) {
            compressionCriteria.appendPath(path);
        } else {
            compressionCriteria.appendPath(".").append(path);
        }
    }

    private List<String> getArgumentList(int index, Object[] methodArguments ) {
        List<String> expansionsList = new ArrayList<>();
        if (index > 0) {
            String[] expansionArray = (String[]) methodArguments[index];
            if (expansionArray != null) {
                expansionsList.addAll(Arrays.asList(expansionArray));
            }
        }
        return expansionsList;
    }

    private List<String> getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        List<String> parameterNames = new ArrayList<>();
        for (Parameter parameter : parameters) {
            if (!parameter.isNamePresent()) {
                throw new IllegalArgumentException("Parameter names are not present!");
            }
            String parameterName = parameter.getName();
            parameterNames.add(parameterName);
        }
        return parameterNames;
    }

    private Method getMethodFromJoinPoint(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException {
        Signature methodSignature = proceedingJoinPoint.getSignature();
        MethodSignature signature = (MethodSignature) methodSignature;
        return proceedingJoinPoint.getTarget().getClass()
                .getMethod(signature.getMethod().getName(), signature.getMethod().getParameterTypes());
    }
}
