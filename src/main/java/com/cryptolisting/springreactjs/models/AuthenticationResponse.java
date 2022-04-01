package com.cryptolisting.springreactjs.models;

public record AuthenticationResponse(String accessToken, String name) {

    public String getName() {
        return name;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
