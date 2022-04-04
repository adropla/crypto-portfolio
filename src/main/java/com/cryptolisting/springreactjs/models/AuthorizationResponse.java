package com.cryptolisting.springreactjs.models;

import java.util.List;

public class AuthorizationResponse {

    private final Enum<ResponseCodes> status;
    private final String message;
    private List<Object> data;

    public AuthorizationResponse(Enum<ResponseCodes> status, String message) {
        this.status = status;
        this.message = message;
    }

    public AuthorizationResponse(Enum<ResponseCodes> status, String message, List<Object> data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Enum<ResponseCodes> getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<Object> getData() {
        return data;
    }
}
