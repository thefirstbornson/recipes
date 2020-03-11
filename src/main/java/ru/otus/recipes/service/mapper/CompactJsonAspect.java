package ru.otus.recipes.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeCreator;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import ru.otus.recipes.service.mapper.CompactJson;

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

        String expansions = compactJsonAnnotation.expansions();
        String includings = compactJsonAnnotation.includings();
        Object[] methodsArguments = pjp.getArgs();

        List<String> expansionsList = new ArrayList<>();
        int methodArgumentIndex = parametersNames.indexOf(expansions);
        if (methodArgumentIndex > 0) {
            String[] expansionArray = (String[]) methodsArguments[methodArgumentIndex];
            if (expansionArray != null) {
                expansionsList.addAll(Arrays.asList(expansionArray));
            }
        }

        List<String> includingsList = new ArrayList<>();
        methodArgumentIndex = parametersNames.indexOf(includings);
        if (methodArgumentIndex > 0) {
            String[] includingArray = (String[]) methodsArguments[methodArgumentIndex];
            if (includingArray != null) {
                includingsList.addAll(Arrays.asList(includingArray));
            }
        }


        ResponseEntity<?> responseEntity = (ResponseEntity<?>) pjp.proceed();
        JsonNode jsonNode = objectMapper.valueToTree(responseEntity.getBody());

        print(jsonNode, expansionsList, includingsList, new StringBuilder());

        responseEntity = ResponseEntity.ok(jsonNode);
        return responseEntity;
    }

    void print(final JsonNode node, List<String> expansions, List<String> includings, StringBuilder currentPath) {
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            final String key = field.getKey();
            System.out.println("Key: " + key);
            final JsonNode value = field.getValue();
            if (value.isObject()) {
                if (currentPath.length() == 0) {
                    currentPath.append(key);
                } else {
                    currentPath.append(".").append(key);
                }

                if (!expansions.contains(currentPath.toString()) &
                        expansions.stream().noneMatch(expansion -> expansion.contains(currentPath.toString() + ".")) &
                        !includings.contains(currentPath.toString())) {
                    ObjectNode objectNode = (ObjectNode) value;
                    List<String> ignoredFields = new ArrayList<>();
                    ignoredFields.add("id");
                    objectNode.retain(ignoredFields);
                } else {
                    print(value, expansions, includings, currentPath);
                }

                if (currentPath.lastIndexOf(".") > 0) {
                    currentPath.replace(currentPath.lastIndexOf("."), currentPath.length(), "");
                } else if (currentPath.length() > 0) {
                    currentPath.setLength(0);
                }

            } else if (value.isArray()) {
                System.out.println("Value: " + value);

                if (currentPath.length() == 0) {
                    currentPath.append(key);
                } else {
                    currentPath.append(".").append(key);
                }

                ArrayNode arrayNode = (ArrayNode) value;
                if (!includings.contains(currentPath.toString()) & includings.stream().noneMatch(including -> including.contains(currentPath.toString() + "."))) {
                    arrayNode.removeAll();
                } else {
                    arrayNode.forEach(arrayNode1 -> {
                        print(arrayNode1, expansions, includings, currentPath);
                    });
                }
                if (currentPath.lastIndexOf(".") > 0) {
                    currentPath.replace(currentPath.lastIndexOf("."), currentPath.length(), "");
                } else if (currentPath.length() > 0) {
                    currentPath.setLength(0);
                }
            }
        }
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
