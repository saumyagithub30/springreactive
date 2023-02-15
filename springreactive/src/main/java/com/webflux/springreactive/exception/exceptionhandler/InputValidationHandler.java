package com.webflux.springreactive.exception.exceptionhandler;

import com.webflux.springreactive.dto.InputFailedValidationResponse;
import com.webflux.springreactive.exception.InputValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InputValidationHandler {

    @ExceptionHandler(InputValidationException.class)
    public ResponseEntity<InputFailedValidationResponse> handleException(InputValidationException ex) {
        InputFailedValidationResponse response = InputFailedValidationResponse.builder()
                .errorCode(ex.getErrorCode())
                .input(ex.getInput())
                .message(ex.getMessage())
                .build();
        return ResponseEntity.badRequest().body(response);
    }
}
