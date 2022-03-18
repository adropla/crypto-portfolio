package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.UpdateRequest;
import com.cryptolisting.springreactjs.models.User;
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


}
