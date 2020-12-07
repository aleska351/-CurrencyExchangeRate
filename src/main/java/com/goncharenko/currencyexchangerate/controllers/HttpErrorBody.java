package com.goncharenko.currencyexchangerate.controllers;

public class HttpErrorBody {
    private String errorMessage;

    public HttpErrorBody(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public HttpErrorBody() {
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
