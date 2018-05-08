package com.ben;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class NonRetryableDownstreamServiceException extends RuntimeException {
    private HttpStatus status;

    public NonRetryableDownstreamServiceException(Throwable cause, HttpStatus status) {
        super(cause);
        this.status = status;
    }
}
