package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.AuthenticationResponse;
import com.cryptolisting.springreactjs.models.RefreshTokenRequest;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.cryptolisting.springreactjs.util.RefreshTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private RefreshTokenUtil refreshTokenUtil;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    public ResponseEntity<?> refresh(RefreshTokenRequest refreshTokenRequest) {
        final String refreshToken = refreshTokenRequest.getRefresh();

        if (refreshTokenUtil.isTokenExpired(refreshToken)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String email = refreshTokenUtil.extractEmail(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByEmail(email);

        String accessToken = accessTokenUtil.generateToken(userDetails, 10);

        AuthenticationResponse response = new AuthenticationResponse(accessToken, refreshToken);

        return ResponseEntity.ok(response);
    }

}
