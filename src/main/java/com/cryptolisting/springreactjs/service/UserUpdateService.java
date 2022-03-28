package com.cryptolisting.springreactjs.service;

import com.cemiltokatli.passwordgenerate.Password;
import com.cemiltokatli.passwordgenerate.PasswordType;
import com.cryptolisting.springreactjs.models.UpdateRequest;
import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.cryptolisting.springreactjs.util.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserUpdateService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    public ResponseEntity<?> update(UpdateRequest request) {
        String email = request.getEmail();
        Optional<User> optUser = userRepository.findByEmail(email);
        optUser.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));
        User user = optUser.get();

        if (passwordEncoder.matches(request.getOldPwd(), user.getPassword())
                && passwordValidator.validate(request.getNewPwd())) {
            user.setPassword(passwordEncoder.encode(request.getNewPwd()));
            userRepository.save(user);
            return ResponseEntity.ok("Updated without any errors.");
        }

        return new ResponseEntity(HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity<?> reset(String email) {
        Password pwdGenerator = Password.createPassword(PasswordType.ALL, 16, 24);
        String newPassword = pwdGenerator.generate();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        user.setResetPwd(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        String jwt =  accessTokenUtil.generateToken(userDetailsService.loadUserByEmail(email), 1);
        emailService.send(email,
                "New password: " + newPassword + "<br>Confirmation link:  " + "<a href=\"https://best-crypto-portfolio.herokuapp.com/api/v1/user/reset-confirmation/" + jwt + "\">link</a>");
        return ResponseEntity.ok("Check email for confirmation.");
    }

    public ResponseEntity<?> confirmReset(String jwt) {
        try {
            if (accessTokenUtil.isTokenExpired(jwt)) {
                return ResponseEntity.ok("Token is expired!");
            };
        } catch(Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String email = accessTokenUtil.extractEmail(jwt);
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        User user = userOptional.get();
        user.setPassword(user.getResetPwd());
        userRepository.save(user);
        return ResponseEntity.ok("You can now log in with new password.");
    }


}
