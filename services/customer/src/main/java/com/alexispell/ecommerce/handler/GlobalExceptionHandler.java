package com.alexispell.ecommerce.handler;


import com.alexispell.ecommerce.exception.CustomerNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handle(CustomerNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException e) {
        var errors = new HashMap<String, String>();
        e.getBindingResult().getAllErrors()
                .forEach(error -> errors.put(((FieldError)error).getField(), error.getDefaultMessage()));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handle(MissingServletRequestParameterException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(Map.of("error", e.getMessage())));
    }
}
