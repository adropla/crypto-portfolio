package com.cryptolisting.springreactjs.controller;

import com.cryptolisting.springreactjs.models.RegistrationRequest;
import com.cryptolisting.springreactjs.service.RegistrationService;
import com.cryptolisting.springreactjs.service.SecurityUserDetailsService;
import com.cryptolisting.springreactjs.models.AuthenticationRequest;
import com.cryptolisting.springreactjs.models.AuthenticationResponse;
import com.cryptolisting.springreactjs.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

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
        return registrationResponse
                ? ResponseEntity.ok("<h1>Registration was successful</h1>")
                : ResponseEntity.ok("Email should be valid. Minimum eight characters, at least one uppercase letter, one lowercase letter and one number.");
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

}