package com.ben;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class RetryableDownstreamServiceException extends RuntimeException {
    private HttpStatus status;

    public RetryableDownstreamServiceException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }
}
