package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.RegistrationRequest;
import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.util.EmailValidator;
import com.cryptolisting.springreactjs.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegistrationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailValidator emailValidator;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean register(RegistrationRequest request) {
        User user = new User();

        String email = request.getEmail();
        String password = request.getPassword();

        if (!emailValidator.validate(email) || !passwordValidator.validate(password))
            return false;

        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            return false;
        }

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(false);
        user.setRoles("ROLE_USER");
        user.setName(email.split("@")[0]);

        userRepository.save(user);

        return true;
    }
}
