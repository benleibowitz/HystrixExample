package com.ben;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class ExceptionMapper extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = {IllegalStateException.class})
    protected ResponseEntity<Object> handleIllegalState(RuntimeException ex, WebRequest request) {
        log.error("Exception", ex);
        return handleExceptionInternal(ex, ErrorResponse.builder()
                        .message(ex.getMessage())
                        .errorType("HYSTRIX_EXAMPLE_APP_EXCEPTION")
                        .build(),
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

    @ExceptionHandler(value = {HystrixRuntimeException.class})
    protected ResponseEntity<Object> handleHystrix(RuntimeException ex, WebRequest request) {
        log.error("Hystrix exception", ex);
        return handleExceptionInternal(ex, ErrorResponse.builder()
                        .message("An internal service error occurred while processing the request")
                        .errorType("EXAMPLE_SERVICE_INTERNAL_SERVER_EXCEPTION")
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> catchAll(RuntimeException ex, WebRequest request) {
        log.error("Handling exception", ex);
        return handleExceptionInternal(ex, ErrorResponse.builder()
                        .message("Unknown internal error occurred")
                        .errorType("INTERNAL_SERVER_ERROR")
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}