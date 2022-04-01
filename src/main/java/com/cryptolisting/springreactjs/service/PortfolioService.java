package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.*;
import com.cryptolisting.springreactjs.util.AccessTokenUtil;
import com.cryptolisting.springreactjs.util.PortfolioUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    @Autowired
    PortfolioRepository portfolioRepository;

    @Autowired
    AccessTokenUtil accessTokenUtil;

    @Autowired
    TransactionService transactionService;

    @Autowired
    PortfolioUtil portfolioUtil;

    public ResponseEntity<?> save(HttpServletRequest request) {

        Portfolio portfolio = new Portfolio();

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
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
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return ResponseEntity.ok(portfolio + " was successfully saved!");
    }
  
  public ResponseEntity<?> change(HttpServletRequest httprequest) {
        String authorizationHeader = httprequest.getHeader("Authorization");

        if (authorizationHeader == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Integer id;
        String email, jwt, name;
        IdNameRequest request;
        Portfolio portfolio = null;

        try {
            String rawJson = httprequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            request = new Gson().fromJson(rawJson, IdNameRequest.class);
            id = request.getId();
            name = request.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = accessTokenUtil.extractEmail(jwt);
                if (name != null && email != null) {
                    portfolio = portfolioRepository.getById(id);
                    if (email.equals(portfolio.getEmail())) {
                        portfolio.setName(name);
                        portfolioRepository.save(portfolio);
                    } else {
                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

      assert portfolio != null;
      return ResponseEntity.ok(portfolio + " was successfully updated!");
    }
  
  public ResponseEntity<?> delete(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String email, jwt;
        Integer id = null;

        try {
            String rawJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            IdRequest idRequest = new Gson().fromJson(rawJson, IdRequest.class);
            id = idRequest.getId();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Portfolio portfolio = null;

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = accessTokenUtil.extractEmail(jwt);
                if (id != null && email != null) {
                    portfolio = portfolioRepository.getById(id);
                    if (email.equals(portfolio.getEmail())) {
                        portfolioRepository.delete(portfolio);
                    } else {
                        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

      assert portfolio != null;
      return ResponseEntity.ok(portfolio + " was successfully deleted!");
    }
  
  

    public ResponseEntity<?> load(HttpServletRequest request) {
        List<Portfolio> list;

        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        String jwt, email;

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = accessTokenUtil.extractEmail(jwt);
                if (email != null) {
                    list = portfolioRepository.findByEmail(email);

                    if (list.size() == 0)
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

                    Gson gson = new Gson();
                    String json = gson.toJson(list);
                    return ResponseEntity.ok(json);
                } else {
                    return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<?> getValue(Integer id) {
        List<Transaction> transactions = transactionService.getTransactions(id);
        HashMap<String, List<HistoricalPrice>> historicalPrices = portfolioUtil.getHistoricalPrices(transactions);
        TreeMap<Long, HashMap<String, Double>> quantites = portfolioUtil.getCurrenciesQuantities(transactions);
        TreeMap<Long, Double> values = portfolioUtil.getPortfolioHistoryValue(historicalPrices, quantites);
        return ResponseEntity.ok(values);
    }

}
