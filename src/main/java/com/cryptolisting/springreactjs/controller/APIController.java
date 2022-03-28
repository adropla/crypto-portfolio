package com.cryptolisting.springreactjs.controller;

import com.cryptolisting.springreactjs.models.*;
import com.cryptolisting.springreactjs.service.*;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@Controller
@RequestMapping("/")
public class APIController {

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private ConfirmationService confirmationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserUpdateService userUpdateService;

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private AccessTokenUtil accessTokenUtil;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/")
    public ModelAndView home() {
        return new ModelAndView("index");
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return "<h1>TEST WAS SUCCESSFUL!</h1>";
    }

    @PutMapping("api/v1/user/name")
    public ResponseEntity<?> changeName(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String email, jwt, name = null;

        try {
            String rawJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            NameRequest nameRequest = new Gson().fromJson(rawJson, NameRequest.class);
            name = nameRequest.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = accessTokenUtil.extractEmail(jwt);
                Optional<User> user = userRepository.findByEmail(email);
                user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
                User userCredentials = user.get();
                userCredentials.setName(name);
                userRepository.save(userCredentials);
                return ResponseEntity.ok("ok");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
      
    @PostMapping("api/v1/portfolio/save")
    public ResponseEntity<?> portfolioSave(HttpServletRequest request) {
        return portfolioService.save(request);
    }

    @GetMapping("api/v1/portfolio/load")
    public ResponseEntity<?> portfolioLoad(HttpServletRequest request) {
        return portfolioService.load(request);
    }

    @PutMapping("api/v1/portfolio/change")
    public ResponseEntity<?> portfolioChange(HttpServletRequest request) {
        return portfolioService.change(request);
    }
  
    @DeleteMapping("api/v1/portfolio/delete")
    public ResponseEntity<?> porftolioDelete(HttpServletRequest request) {
        return portfolioService.delete(request);
    }

    @PostMapping("api/v1/transaction/save")
    public ResponseEntity<?> transactionSave(@RequestBody TransactionRequest request) {
        return transactionService.save(request);
    }

    @PostMapping("api/v1/transaction/load")
    public ResponseEntity<?> transactionLoad(@RequestBody IdRequest request) {
        return transactionService.loadAllByPortfolioId(request.getId());
    }

    @DeleteMapping("api/v1/transaction/delete")
    public ResponseEntity<?> transactionDelete(@RequestBody IdRequest request) {
        return transactionService.delete(request.getId());
    }

    @PostMapping("api/v1/auth/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request, HttpServletResponse response) {
        return ResponseEntity.ok(refreshTokenService.refresh(request, response));
    }

    @PostMapping("api/v1/watchlist/save")
    public ResponseEntity<?> watchlistSave(HttpServletRequest request) {
        return watchlistService.save(request);
    }

    @PostMapping("api/v1/auth/registration")
    public ResponseEntity<?> registration(@RequestBody RegistrationRequest request) {
        boolean registrationResponse = registrationService.register(request);
        if (registrationResponse) {
            String email = request.getEmail();
            String jwt =  accessTokenUtil.generateToken(userDetailsService.loadUserByEmail(email), 10);
            emailService.send(email, "<a href=\"https://best-crypto-portfolio.herokuapp.com/api/v1/auth/confirmation/" + jwt + "\">link</a>");
            return ResponseEntity.ok("ok");
        } else {
            return new ResponseEntity(HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("api/v1/auth/confirmation/{jwt}")
    public ResponseEntity<?> confirmation(@PathVariable String jwt) {
        try {
            if (accessTokenUtil.isTokenExpired(jwt)) {
                return ResponseEntity.ok("Token is expired!");
            };
        } catch(Exception ex) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        String email = accessTokenUtil.extractEmail(jwt);
        boolean confirmationResponse = confirmationService.confirm(jwt);
        return confirmationResponse
                ? ResponseEntity.ok(email + " was successfully activated!")
                : new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("api/v1/auth/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws Exception {

        return authenticationService.authenticate(authenticationRequest, response);
    }

    @PutMapping("api/v1/user/update")
    public ResponseEntity<?> updateUser(@RequestBody UpdateRequest updateRequest) throws  Exception {
        return userUpdateService.update(updateRequest);
    }
}