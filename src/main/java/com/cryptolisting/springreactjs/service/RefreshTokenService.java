package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.AuthenticationResponse;
import com.cryptolisting.springreactjs.models.RefreshTokenRequest;
import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.cryptolisting.springreactjs.util.RefreshTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RefreshTokenService {

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private RefreshTokenUtil refreshTokenUtil;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> refresh(RefreshTokenRequest refreshTokenRequest) {
        final String refreshToken = refreshTokenRequest.getRefresh();

        if (refreshTokenUtil.isTokenExpired(refreshToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email = refreshTokenUtil.extractEmail(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByEmail(email);

        String accessToken = accessTokenUtil.generateToken(userDetails, 10);

        Optional<User> userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        final String name = user.getName();

        AuthenticationResponse response = new AuthenticationResponse(accessToken, name);

        return ResponseEntity.ok(response);
    }

}
