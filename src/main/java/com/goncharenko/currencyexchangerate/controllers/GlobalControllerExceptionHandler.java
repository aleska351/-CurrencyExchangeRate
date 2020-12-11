package com.goncharenko.currencyexchangerate.controllers;

import com.goncharenko.currencyexchangerate.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<HttpErrorBody> handlerResourceNotFound(ResourceNotFoundException exception) {
        return ResponseEntity.status(404).body(new HttpErrorBody(exception.getMessage()));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<HttpErrorBody> handlerResourceNotFound(Exception exception) {
        return ResponseEntity.status(500).body(new HttpErrorBody(exception.getMessage()));
    }
}
