package com.dev.mosquera.usermanager.service.implementation;

import com.dev.mosquera.usermanager.repository.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository repository;

    CustomUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found: " + username));
        return ;
    }
}
