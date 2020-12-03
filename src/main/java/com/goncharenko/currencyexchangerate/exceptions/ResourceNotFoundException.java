package com.goncharenko.currencyexchangerate.exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {

        this.message = message;
    }

    private String message;

}
