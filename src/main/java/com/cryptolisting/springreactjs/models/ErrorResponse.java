package com.cryptolisting.springreactjs.models;

public class ErrorResponse {
    private final String error;

    public ErrorResponse(Exception error) {
        this.error = error.toString();
    }

    public String getError() {
        return error;
    }
}
