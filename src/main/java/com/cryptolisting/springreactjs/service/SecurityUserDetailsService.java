package com.cryptolisting.springreactjs.service;

import com.cryptolisting.springreactjs.models.SecurityUserDetails;
import com.cryptolisting.springreactjs.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    public List<Integer> loadInactiveIds() {
        List<User> list = userRepository.findAll();
        List<Integer> ids = new ArrayList<>();
        list.forEach(u -> {
            if (!u.isActive()) {
                ids.add(u.getId());
            }
        });
        return ids;
    }

    public UserDetails loadUserByEmail(String email) {
        return loadUserByUsername(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + email));

        return user.map(SecurityUserDetails::new).get();
    }
}
