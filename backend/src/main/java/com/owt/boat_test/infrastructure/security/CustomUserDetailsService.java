package com.owt.boat_test.infrastructure.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Simple hardcoded user for the technical test
    private static final String TEST_USERNAME = "admin";
    private static final String TEST_PASSWORD = "password";
    
    private final BCryptPasswordEncoder passwordEncoder;
    
    public CustomUserDetailsService() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!TEST_USERNAME.equals(username)) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        
        return new User(
            TEST_USERNAME, 
            passwordEncoder.encode(TEST_PASSWORD), 
            Collections.emptyList()
        );
    }
    
    public boolean verifyPassword(String rawPassword, String username) {
        if (!TEST_USERNAME.equals(username)) {
            return false;
        }
        
        return TEST_PASSWORD.equals(rawPassword);
    }
} 