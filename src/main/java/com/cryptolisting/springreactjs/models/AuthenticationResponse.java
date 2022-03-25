package com.cryptolisting.springreactjs.models;

public class AuthenticationResponse {

    private final String accessToken;
    private final String name;

    public AuthenticationResponse(String accessToken, String name) {
        this.accessToken = accessToken;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
