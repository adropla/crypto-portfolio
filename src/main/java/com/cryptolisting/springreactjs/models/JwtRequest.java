package com.cryptolisting.springreactjs.models;

public record JwtRequest(String jwt) {
    public String getJwt() {
        return jwt;
    }
}
