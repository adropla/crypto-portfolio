package com.cryptolisting.springreactjs.models;

public class EmailUpdateRequest {

    private String oldEmail;
    private String newEmail;
    private String password;

    public EmailUpdateRequest() {
    }

    public EmailUpdateRequest(String oldEmail, String newEmail, String password) {
        this.oldEmail = oldEmail;
        this.newEmail = newEmail;
        this.password = password;
    }

    public String getOldEmail() {
        return oldEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public String getPassword() {
        return password;
    }
}
