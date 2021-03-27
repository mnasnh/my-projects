package com.myretail.myretailproductservice.exception;

import com.myretail.myretailproductservice.web.model.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler({ ApiException.class })
    protected ResponseEntity<ErrorResponse> handleApiException(ApiException ex) {
        return new ResponseEntity<>(new ErrorResponse(ex.getStatus(), ex.getMessage(), Instant.now()), ex.getStatus());
    }

}
