package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.AuthenticationRequest;
import com.cryptolisting.springreactjs.models.AuthenticationResponse;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.servlet.http.HttpServletResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class AuthenticationServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Test
    void authenticate() {

        String email = "lev_gempel@inbox.ru";
        String password = "1A2a3A4a";

        AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, password);
        HttpServletResponse response = new MockHttpServletResponse();

        ResponseEntity<?> responseEntity = authenticationService.authenticate(authenticationRequest, response);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        AuthenticationResponse authenticationResponse = (AuthenticationResponse) responseEntity.getBody();
        UserDetails userDetails = userDetailsService.loadUserByEmail(email);
        assertTrue(accessTokenUtil.validateToken(authenticationResponse.getAccessToken(), userDetails));
    }

    @Test
    void logout() {
        HttpServletResponse response = new MockHttpServletResponse();
        authenticationService.logout(response);
        assertEquals("refresh=; Path=/; Max-Age=0; Expires=Thu, 1 Jan 1970 00:00:00 GMT; Secure; HttpOnly; SameSite=None",
                response.getHeader("Set-Cookie"));
    }
}