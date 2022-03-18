package com.cryptolisting.springreactjs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleaningService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityUserDetailsService userDetailsService;

    public void clean() {
        List<Integer> ids = userDetailsService.loadInactiveIds();
        userRepository.deleteAllById(ids);
        System.out.println("Successfully deleted: " + ids.size() + " users!");
    }

}
