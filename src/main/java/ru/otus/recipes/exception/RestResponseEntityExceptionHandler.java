package ru.otus.recipes.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
* Класс является обработчиком исключений, возникающий в контроллере
*/
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

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
        String bodyOfResponse = ex.getMessage();
        log.error("Can not find entity", ex);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {EntityExistsException.class})
    protected ResponseEntity<Object> handleEntityExistsException(Exception ex, WebRequest request) {
        String bodyOfResponse = ex.getMessage();
        log.error("Can not save entity", ex);
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), HttpStatus.CONFLICT, request);
    }
}
