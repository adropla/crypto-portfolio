package com.cryptolisting.springreactjs.models;

public record ConfirmationRequest(String jwt) {

    public String getJwt() {
        return jwt;
    }
}
