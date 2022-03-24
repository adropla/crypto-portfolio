package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.AuthenticationRequest;
import com.cryptolisting.springreactjs.models.AuthenticationResponse;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.cryptolisting.springreactjs.util.RefreshTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthenticationService {

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private RefreshTokenUtil refreshTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        System.out.println(authenticationRequest.toString());
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            System.out.println("Authentication was successful!");
        } catch (AuthenticationException ex) {
            System.out.println("Authentication was NOT successful!");
            ex.printStackTrace();
            throw new BadCredentialsException("Wrong email or password, or something happened in the process.");
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByEmail(authenticationRequest.getEmail());

        final String accessToken = accessTokenUtil.generateToken(userDetails, 10);
        final String refreshToken = refreshTokenUtil.generateToken(userDetails);

        Cookie cookie = new Cookie("refresh",refreshToken);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(new AuthenticationResponse(accessToken));
    }

}
