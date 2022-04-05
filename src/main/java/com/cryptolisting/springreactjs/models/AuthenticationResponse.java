package com.cryptolisting.springreactjs.models;

import java.util.Objects;

public final class AuthenticationResponse {
    private final String accessToken;
    private final String name;

    public AuthenticationResponse(String accessToken, String name) {
        this.accessToken = accessToken;
        this.name = name;
    }

    public String accessToken() {
        return accessToken;
    }

    public String name() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (AuthenticationResponse) obj;
        return Objects.equals(this.accessToken, that.accessToken) &&
                Objects.equals(this.name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accessToken, name);
    }

    @Override
    public String toString() {
        return "AuthenticationResponse[" +
                "accessToken=" + accessToken + ", " +
                "name=" + name + ']';
    }

    public String getName() {
        return name;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
