package com.cryptolisting.springreactjs.controller;

import com.cryptolisting.springreactjs.models.*;
import com.cryptolisting.springreactjs.service.*;
import com.cryptolisting.springreactjs.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.SignatureException;

@Controller
public class APIController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ConfirmationService confirmationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserUpdateService userUpdateService;

    @GetMapping("")
    public ModelAndView home() {
        ModelAndView mav = new ModelAndView("index");
        return mav;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "<h1>TEST WAS SUCCESSFUL!</h1>";
    }

    @PostMapping("api/v1/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest request) {
        boolean registrationResponse = registrationService.register(request);
        if (registrationResponse) {
            String email = request.getEmail();
            String jwt =  jwtTokenUtil.generateToken(userDetailsService.loadUserByEmail(email));
            emailService.send(email, "<a href=\"http://localhost:8080/api/v1/confirmation/" + jwt + "\">link</a>");
            return ResponseEntity.ok("ok");
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("api/v1/confirmation/{jwt}")
    public ResponseEntity<?> confirmation(@PathVariable String jwt) {
        try {
            if (jwtTokenUtil.isTokenExpired(jwt)) {
                return ResponseEntity.ok("Token is expired!");
            };
        } catch(Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String email = jwtTokenUtil.extractEmail(jwt);
        boolean confirmationResponse = confirmationService.confirm(jwt);
        return confirmationResponse
                ? ResponseEntity.ok(email + " was successfully activated!")
                : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("api/v1/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        System.out.println(authenticationRequest.toString());
        try {
            System.out.println(" --- Authentication try starts here --- ");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
            );
            System.out.println("Authentication was successful!");
        } catch (AuthenticationException ex) {
            System.out.println("Authentication was NOT successful!");
            ex.printStackTrace();
            throw new BadCredentialsException("Wrong email or password, or something happened in the process.");
        }

        System.out.println("Trying to fetch userDetails from userDetailsService.loadUserByUsername");

        final UserDetails userDetails = userDetailsService
                .loadUserByEmail(authenticationRequest.getEmail());

        System.out.println("Current userDetails: " + userDetails.getUsername());

        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt));
    }

    @PutMapping("api/v1/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateRequest updateRequest) throws  Exception {
        return userUpdateService.update(updateRequest);
    }
}