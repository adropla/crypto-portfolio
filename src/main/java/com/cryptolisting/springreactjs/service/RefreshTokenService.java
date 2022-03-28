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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

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

    public ResponseEntity<?> refresh(RefreshTokenRequest refreshTokenRequest, HttpServletResponse httpResponse) {
        final String refreshToken = refreshTokenRequest.getRefresh();

        if (refreshTokenUtil.isTokenExpired(refreshToken)) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String email = refreshTokenUtil.extractEmail(refreshToken);

        UserDetails userDetails = userDetailsService.loadUserByEmail(email);

        String accessToken = accessTokenUtil.generateToken(userDetails, 10);


        Cookie cookie = new Cookie("refresh",refreshToken);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        httpResponse.addCookie(cookie);

        final String name = userRepository.findByEmail(email).get().getName();

        AuthenticationResponse response = new AuthenticationResponse(accessToken, name);

        return ResponseEntity.ok(response);
    }

}
