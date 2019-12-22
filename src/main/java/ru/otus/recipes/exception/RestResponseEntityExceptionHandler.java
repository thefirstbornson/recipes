package ru.otus.recipes.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
* Класс является обработчиком исключений, возникающий в контроллере
*/
@RestControllerAdvice
public class RestResponseEntityExceptionHandler{

    private final Logger log = LoggerFactory.getLogger(getClass().getTypeName());

    /**
     * Метод отвечает за перехват исключения EntityNotFoundException
     * Возвращает ResponseEntity с кодом ответа 404, и сообщением об ошибке.
     * @param ex объект исключения (EntityNotFoundException)
     * @param request объект WebRequest
     * @return ResponseEntity с кодом ответа 404, и сообщение об ошибке.
     */
    @ExceptionHandler(value = {EntityNotFoundException.class})
    protected ResponseEntity<Object> handleEntityNotFoundException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(getBody(HttpStatus.NOT_FOUND, ex,
                "Can not find entity"), new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(value = {EntityExistsException.class})
    protected ResponseEntity<Object> handleEntityExistsException(Exception ex, WebRequest request) {
        return new ResponseEntity<>(getBody(HttpStatus.NOT_FOUND, ex,
                "Can not save entity"), new HttpHeaders(), HttpStatus.CONFLICT);
    }

    private Map<String, Object> getBody(HttpStatus status, Exception ex, String message) {
        log.error(message, ex);
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", message);
        body.put("timestamp", new Date());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("exception", ex.toString());
        Throwable cause = ex.getCause();
        if (cause != null) {
            body.put("exceptionCause", ex.getCause().toString());
        }
        return body;
    }
}
