package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.User;
import com.cryptolisting.springreactjs.models.UserWatchlist;
import com.cryptolisting.springreactjs.models.WatchlistRequest;
import com.cryptolisting.springreactjs.util.JwtUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WatchlistService {

    @Autowired
    private WatchlistRepository watchlistRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public WatchlistService() {
    }

    public ResponseEntity<?> save(HttpServletRequest request) {

        String authorizationHeader = request.getHeader("Authorization");
        String email, jwt, watchlist = null;
        UserWatchlist userWatchlist = null;

        try {
            String rawJson = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            System.out.println(rawJson);
            WatchlistRequest watchlistRequest = new Gson().fromJson(rawJson, WatchlistRequest.class);
            System.out.println(watchlistRequest.toString());
            watchlist = watchlistRequest.getWatchlist();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = jwtUtil.extractEmail(jwt);
                Optional<User> user = userRepository.findByEmail(email);
                user.orElseThrow(() -> new UsernameNotFoundException("User not found."));
                User userCredentials = user.get();
                userWatchlist = new UserWatchlist(userCredentials.getId(), watchlist);
                watchlistRepository.save(userWatchlist);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return ResponseEntity.ok("Successfully saved watchlist \"" + userWatchlist.getWatchlist()
                + "\" for userID -> " + userWatchlist.getId());
    }
}