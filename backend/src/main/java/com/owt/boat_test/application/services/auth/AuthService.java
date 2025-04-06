package com.owt.boat_test.application.services.auth;

import org.springframework.stereotype.Service;

import com.owt.boat_test.application.dtos.auth.JwtResponse;
import com.owt.boat_test.application.dtos.auth.LoginRequest;
import com.owt.boat_test.infrastructure.security.CustomUserDetailsService;
import com.owt.boat_test.infrastructure.security.JwtProvider;

/**
 * Service class that handles authentication-related business logic.
 * Responsible for user authentication and JWT token generation.
 */
@Service
public class AuthService {
    
    private final CustomUserDetailsService userDetailsService;
    private final JwtProvider jwtProvider;
    
    /**
     * Constructor for AuthService with dependency injection.
     * 
     * @param userDetailsService Service for loading and validating user details
     * @param jwtProvider Service for JWT token generation and validation
     */
    public AuthService(CustomUserDetailsService userDetailsService, JwtProvider jwtProvider) {
        this.userDetailsService = userDetailsService;
        this.jwtProvider = jwtProvider;
    }
    
    /**
     * Authenticates a user based on provided credentials and generates a JWT token.
     * 
     * @param loginRequest Object containing username and password
     * @return JwtResponse containing the generated token
     * @throws RuntimeException if the credentials are invalid
     */
    public JwtResponse authenticate(LoginRequest loginRequest) {
        if (!userDetailsService.verifyPassword(loginRequest.getPassword(), loginRequest.getUsername())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        String token = jwtProvider.generateToken(loginRequest.getUsername());
        
        return new JwtResponse(token);
    }
} 