package ru.otus.recipes.service.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Aspect
@Component
public class CompactJsonAspect {

    private final ObjectMapper objectMapper;

    public CompactJsonAspect(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Around("@annotation(ru.otus.recipes.service.mapper.CompactJson)")
    public ResponseEntity makeCompact(ProceedingJoinPoint pjp) throws Throwable {
        ResponseEntity responseEntity = (ResponseEntity) pjp.proceed();
        JsonNode jsonNode = objectMapper.valueToTree(responseEntity.getBody());
        responseEntity= ResponseEntity.ok(jsonNode);
        return responseEntity;
    }
}
