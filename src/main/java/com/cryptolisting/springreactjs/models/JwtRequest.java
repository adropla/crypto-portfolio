package com.cryptolisting.springreactjs.models;

import java.util.Objects;

public final class JwtRequest {
    private final String jwt;

    JwtRequest(String jwt) {
        this.jwt = jwt;
    }

    public String jwt() {
        return jwt;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (JwtRequest) obj;
        return Objects.equals(this.jwt, that.jwt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(jwt);
    }

    @Override
    public String toString() {
        return "JwtRequest[" +
                "jwt=" + jwt + ']';
    }

    public String getJwt() {
        return jwt;
    }
}
