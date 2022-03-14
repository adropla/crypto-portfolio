package com.cryptolisting.springreactjs.controller;

import com.cryptolisting.springreactjs.SecurityUserDetailsService;
import com.cryptolisting.springreactjs.models.AuthenticationRequest;
import com.cryptolisting.springreactjs.models.AuthenticationResponse;
import com.cryptolisting.springreactjs.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class APIController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @GetMapping("")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @GetMapping("/test")
    public String test() {
        return "index";
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException ex) {
            throw new Exception("Incorrect username or password", ex);
        }
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

}