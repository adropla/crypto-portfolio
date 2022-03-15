package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.SecurityUserDetails;
import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ConfirmationService {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public boolean confirm(String jwt) {
        String email = jwtUtil.extractEmail(jwt);
        Optional<User> optional = userRepository.findByEmail(email);
        if (optional.isPresent()) {
            User activatedUser = optional.get();

            if (activatedUser.isActive())
                return false;

            activatedUser.setActive(true);
            userRepository.save(activatedUser);
            return true;
        } else {
            return false;
        }
    }
}
