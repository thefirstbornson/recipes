package ru.otus.recipes.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.stream.Stream;

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
        Map<String, String[]> expansionsByValue = new HashMap<>();
        String[] expansionsArray ={} ;
        if (parametersNames.indexOf(expansions)>0){
            expansionsArray = (String[]) methodsArguments[parametersNames.indexOf(expansions)];
        }


        ResponseEntity<?> responseEntity = (ResponseEntity<?>) pjp.proceed();
        JsonNode jsonNode = objectMapper.valueToTree(responseEntity.getBody());

        print(jsonNode, expansionsArray,new StringBuilder());

        responseEntity= ResponseEntity.ok(jsonNode);
        return responseEntity;
    }

    void print(final JsonNode node, String[] expansionsArray, StringBuilder currentPath ) throws IOException {
        Iterator<Map.Entry<String, JsonNode>> fieldsIterator = node.fields();
        JsonNode jsonNode = JsonNodeFactory.instance.objectNode();

        while (fieldsIterator.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsIterator.next();
            final String key = field.getKey();
            System.out.println("Key: " + key);
            final JsonNode value = field.getValue();
            if (value.isObject()) {
                if (currentPath.length()==0){
                    currentPath.append(key);
                }
//                else {
//                    currentPath.append(".").append(key);
//                }
                if (!Arrays.asList(expansionsArray).contains(currentPath.toString())){
                    ObjectNode objectNode = (ObjectNode) value;
                    objectNode.retain("id");
                }
                currentPath.append(".").append(key);
                print(value,expansionsArray,currentPath);

            } else {
                System.out.println("Value: " + value);
            }
            if(currentPath.lastIndexOf(".")>0) {
                currentPath.replace(currentPath.lastIndexOf("."),currentPath.length()-1,"");
            } else if (currentPath.length()>0) {
                currentPath.setLength(0);
        }
        }
    }

    private List<String> getParameterNames(Method method) {
        Parameter[] parameters = method.getParameters();
        List<String> parameterNames = new ArrayList<>();

        for (Parameter parameter : parameters) {
            if(!parameter.isNamePresent()) {
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
