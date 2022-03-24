package com.cryptolisting.springreactjs.models;

public class RefreshTokenRequest {
    private String refresh;

    public RefreshTokenRequest() {
    }

    public RefreshTokenRequest(String refresh) {
        this.refresh = refresh;
    }

    public String getRefresh() {
        return refresh;
    }
}
