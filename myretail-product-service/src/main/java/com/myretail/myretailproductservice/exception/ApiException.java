package com.myretail.myretailproductservice.exception;

import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {

    private final HttpStatus status;

    public ApiException(HttpStatus status, Throwable cause , String message) {
        super(message , cause);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return this.status;
    }
}
