package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.RegistrationRequest;
import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.util.EmailValidator;
import com.cryptolisting.springreactjs.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    public boolean register(RegistrationRequest request) {
        User user = new User();

        String email = request.getEmail();
        String password = request.getPassword();

        if (!emailValidator.validate(email) || !passwordValidator.validate(password))
            return false;

        user.setEmail(email);
        user.setPassword(password);
        user.setActive(false);
        user.setRoles("ROLE_USER");

        userRepository.save(user);
        return true;
    }
}
