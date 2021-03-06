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
    @ExceptionHandler(value = {HystrixRuntimeException.class})
    protected ResponseEntity<Object> handleHystrix(RuntimeException ex, WebRequest request) {
        log.error("Hystrix exception", ex);
        return handleExceptionInternal(ex, ErrorResponse.builder()
                        .message("An internal service error occurred while processing the request")
                        .errorType("INTERNAL_SERVER_ERROR")
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = {NonRetryableDownstreamServiceException.class})
    protected ResponseEntity<Object> handleNonRetryable(NonRetryableDownstreamServiceException ex, WebRequest request) {
        log.error("Handling non-retryable exception", ex);
        return handleExceptionInternal(ex, ErrorResponse.builder()
                        .message("A non-retryable error occurred while attempting to call the downstream service - " + ex.getMessage())
                        .errorType("ERROR_FROM_DOWNSTREAM_SERVICE")
                        .build(),
                new HttpHeaders(), ex.getStatus(), request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> catchAll(RuntimeException ex, WebRequest request) {
        log.error("Handling exception", ex);
        return handleExceptionInternal(ex, ErrorResponse.builder()
                        .message(ex.getMessage())
                        .errorType("INTERNAL_SERVER_ERROR")
                        .build(),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}