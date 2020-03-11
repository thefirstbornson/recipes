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
public class CompressJsonAspect {

    private final ObjectMapper objectMapper;

    public CompressJsonAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(ru.otus.recipes.service.mapper.CompressJson)")
    public ResponseEntity<?> makeCompressedJson(ProceedingJoinPoint pjp) throws Throwable {
        Method method = getMethodFromJoinPoint(pjp);
        Object[] methodArguments = pjp.getArgs();
        List<String> parametersNames = getParameterNames(method);
        CompressJson compressJsonAnnotation = method.getAnnotation(CompressJson.class);
        CompressionCriteria compressionCriteria = buildCompressionCriteria(parametersNames, methodArguments, compressJsonAnnotation);

        ResponseEntity<?> methodResult = (ResponseEntity<?>) pjp.proceed();
        JsonNode jsonNodeToCompress = objectMapper.valueToTree(methodResult.getBody());
        compressJsonNode(jsonNodeToCompress, compressionCriteria);

        return ResponseEntity.ok(jsonNodeToCompress);
    }

    private CompressionCriteria buildCompressionCriteria(List<String> parametersNames, Object[] methodArguments, CompressJson compressJsonAnnotation) {
        String expansionsParamName = compressJsonAnnotation.expansions();
        String includingsParamName = compressJsonAnnotation.includings();
        String delimiter = compressJsonAnnotation.delimiter();
        String identifier = compressJsonAnnotation.identifier();

        List<String> expansionsList = getArgumentListByIndex(parametersNames.indexOf(expansionsParamName),methodArguments);
        List<String> includingsList = getArgumentListByIndex(parametersNames.indexOf(includingsParamName),methodArguments);

        return CompressionCriteria.builder()
                .includingsList(includingsList)
                .expansionsList(expansionsList)
                .delimiter(delimiter)
                .identifier(identifier)
                .build();
    }

    private void compressJsonNode(final JsonNode node, CompressionCriteria compressionCriteria) {
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            final String key = field.getKey();
            final JsonNode value = field.getValue();

            if (value.isContainerNode()){
                increasePath(compressionCriteria, key);
                if (value.isObject()) {
                    ObjectNode objectNode = (ObjectNode) value;
                    if (areObjectNodeCompressionConditionsMet(compressionCriteria)) {
                        objectNode.retain(compressionCriteria.getIdentifier());
                    } else {
                        compressJsonNode(objectNode,compressionCriteria);
                    }
                } else if (value.isArray()) {
                    ArrayNode arrayNode = (ArrayNode) value;
                    if (areArrayNodeCompressionConditionsMet(compressionCriteria)) {
                        arrayNode.removeAll();
                    } else {
                        arrayNode.forEach(jsonNode -> {compressJsonNode(jsonNode, compressionCriteria);});
                    }
                }
                reducePath(compressionCriteria);
            }
        }
    }

    private boolean areArrayNodeCompressionConditionsMet(CompressionCriteria compressionCriteria) {
        return !compressionCriteria.getIncludingsList().contains(compressionCriteria.getPath().toString()) &
                compressionCriteria.getIncludingsList()
                        .stream()
                        .noneMatch(including -> including.contains(compressionCriteria.getPath().toString() + compressionCriteria.getDelimiter()));
    }

    private boolean areObjectNodeCompressionConditionsMet(CompressionCriteria compressionCriteria) {
        return !compressionCriteria.getExpansionsList().contains(compressionCriteria.getPath().toString()) &
               !compressionCriteria.getIncludingsList().contains(compressionCriteria.getPath().toString()) &
                compressionCriteria.getExpansionsList()
                        .stream()
                        .noneMatch(expansion -> expansion.contains(compressionCriteria.getPath().toString() + compressionCriteria.getDelimiter()));
    }

    private void reducePath(CompressionCriteria compressionCriteria) {
        if (compressionCriteria.getPath().lastIndexOf(compressionCriteria.getDelimiter()) > 0) {
            compressionCriteria.getPath().replace(compressionCriteria.getPath().lastIndexOf(compressionCriteria.getDelimiter()),
                    compressionCriteria.getPath().length(), "");
        } else if (compressionCriteria.getPath().length() > 0) {
            compressionCriteria.getPath().setLength(0);
        }
    }

    private void increasePath(CompressionCriteria compressionCriteria, String path) {
        if (compressionCriteria.getPath().length() == 0) {
            compressionCriteria.appendPath(path);
        } else {
            compressionCriteria.appendPath(compressionCriteria.getDelimiter()).append(path);
        }
    }

    private List<String> getArgumentListByIndex(int index, Object[] methodArguments ) {
        List<String> argumentList = new ArrayList<>();
        if (index > 0) {
            String[] expansionArray = (String[]) methodArguments[index];
            if (expansionArray != null) {
                argumentList.addAll(Arrays.asList(expansionArray));
            }
        }
        return argumentList;
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
