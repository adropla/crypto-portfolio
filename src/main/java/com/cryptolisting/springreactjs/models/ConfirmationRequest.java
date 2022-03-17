package com.cryptolisting.springreactjs.models;

public class ConfirmationRequest {
    private String jwt;

    public ConfirmationRequest(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
