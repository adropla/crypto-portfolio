package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConfirmationService {

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> confirm(String jwt) {
        try {
            if (accessTokenUtil.isTokenExpired(jwt)) {
                return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
            }
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String email = accessTokenUtil.extractEmail(jwt);
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            User activatedUser = optional.get();

            if (activatedUser.isActive())
                return new ResponseEntity<>(HttpStatus.FOUND);

            activatedUser.setActive(true);
            userRepository.save(activatedUser);
            return ResponseEntity.ok(email + " was successfully activated!");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
