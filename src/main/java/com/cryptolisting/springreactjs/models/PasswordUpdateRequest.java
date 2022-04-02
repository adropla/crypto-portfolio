package com.cryptolisting.springreactjs.models;

public class PasswordUpdateRequest {

    private String email;
    private String oldPwd;
    private String newPwd;

    public PasswordUpdateRequest() {
    }

    public PasswordUpdateRequest(String email, String oldPwd, String newPwd) {
        this.email = email;
        this.oldPwd = oldPwd;
        this.newPwd = newPwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPwd() {
        return oldPwd;
    }

    public void setOldPwd(String oldPwd) {
        this.oldPwd = oldPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
