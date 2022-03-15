package com.cryptolisting.springreactjs.util;

import org.springframework.stereotype.Service;

@Service
public class PasswordValidator {

    public boolean validate(String password) {
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");
    }
}
