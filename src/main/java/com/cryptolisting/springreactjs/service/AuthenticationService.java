package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.AuthenticationRequest;
import com.cryptolisting.springreactjs.models.AuthenticationResponse;
import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.cryptolisting.springreactjs.util.RefreshTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<?> authenticate(AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        String email = authenticationRequest.getEmail();
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

        User user = userOptional.get();
        if (!user.isActive()) {
            String jwt =  accessTokenUtil.generateToken(userDetailsService.loadUserByEmail(email), 10);
            emailService.send(email, "<a href=\"https://best-crypto-portfolio.herokuapp.com/api/v1/auth/confirmation/" + jwt + "\">link</a>");
            return new ResponseEntity(HttpStatus.EXPECTATION_FAILED);
        }


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
                .loadUserByEmail(email);

        final String name = userRepository.findByEmail(authenticationRequest.getEmail()).get().getName();

        final String accessToken = accessTokenUtil.generateToken(userDetails, 10);
        final String refreshToken = refreshTokenUtil.generateToken(userDetails);

        Cookie cookie = new Cookie("refresh",refreshToken);
        cookie.setMaxAge(30 * 24 * 60 * 60);
        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(new AuthenticationResponse(accessToken, name));
    }

}
