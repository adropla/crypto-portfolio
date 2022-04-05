package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.RegistrationRequest;
import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.cryptolisting.springreactjs.util.EmailValidator;
import com.cryptolisting.springreactjs.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> register(RegistrationRequest request) {
        User user = new User();

        String email = request.getEmail();
        String password = request.getPassword();

        if (!emailValidator.validate(email) || !passwordValidator.validate(password))
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);

        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.FOUND);
        }

        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setActive(false);
        user.setRoles("ROLE_USER");
        user.setName(email.split("@")[0]);

        userRepository.save(user);

        String jwt =  accessTokenUtil.generateToken(userDetailsService.loadUserByEmail(email), 10);
        emailService.send(email, "https://best-crypto-portfolio.herokuapp.com/api/v1/auth/confirmation/" + jwt);

        return ResponseEntity.ok("check your email");
    }
}
