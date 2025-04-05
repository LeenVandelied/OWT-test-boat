package com.owt.boat_test.application.services.auth;

import org.springframework.stereotype.Service;

import com.owt.boat_test.application.dtos.auth.JwtResponse;
import com.owt.boat_test.application.dtos.auth.LoginRequest;
import com.owt.boat_test.infrastructure.security.CustomUserDetailsService;
import com.owt.boat_test.infrastructure.security.JwtProvider;

@Service
public class AuthService {
    
    private final CustomUserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    
    public AuthService(CustomUserDetailsService userDetailsService, JwtProvider jwtProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
    }
    
    public JwtResponse authenticate(LoginRequest loginRequest) {
        if (!userDetailsService.verifyPassword(loginRequest.getPassword(), loginRequest.getUsername())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtProvider.generateToken(loginRequest.getUsername());
        
        return new JwtResponse(token);
    }
} 