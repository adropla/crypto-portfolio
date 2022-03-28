package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.*;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    AccessTokenUtil accessTokenUtil;

    public ResponseEntity<?> save(HttpServletRequest request) {

        Portfolio portfolio = new Portfolio();

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
        }

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = accessTokenUtil.extractEmail(jwt);
                if (name != null && email != null) {
                    portfolio.setName(name);
                    portfolio.setEmail(email);
                    portfolioRepository.save(portfolio);
                } else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(portfolio.toString() + " was successfully saved!");
    }

    public ResponseEntity<?> load(HttpServletRequest request) {
        List<Portfolio> list = null;

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }

        String jwt, email;

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = accessTokenUtil.extractEmail(jwt);
                if (email != null) {
                    list = portfolioRepository.findByEmail(email);

                    if (list.size() == 0)
                        return new ResponseEntity(HttpStatus.NOT_FOUND);

                    Gson gson = new Gson();
                    String json = gson.toJson(list);
                    return ResponseEntity.ok(json);
                } else {
                    return new ResponseEntity(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
