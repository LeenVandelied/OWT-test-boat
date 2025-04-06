package com.owt.boat_test.infrastructure.adapters.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.owt.boat_test.application.dtos.auth.JwtResponse;
import com.owt.boat_test.application.dtos.auth.LoginRequest;
import com.owt.boat_test.application.services.auth.AuthService;

/**
 * REST Controller for handling authentication-related endpoints.
 * Provides functionality for user login and JWT token generation.
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * Constructor for AuthController with dependency injection.
     * 
     * @param authService The service handling authentication business logic
     */
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Authenticates a user and generates a JWT token.
     * 
     * @param loginRequest Object containing user credentials (username and password)
     * @return ResponseEntity with JWT token on successful authentication or error message on failure
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            JwtResponse jwtResponse = authService.authenticate(loginRequest);
            return ResponseEntity.ok(jwtResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }
}