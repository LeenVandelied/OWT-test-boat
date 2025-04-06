package com.owt.boat_test.application.dtos.auth;

/**
 * Data Transfer Object (DTO) for JWT authentication response.
 * Contains the JWT token and its type that are returned to the client after successful authentication.
 */
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    
    /**
     * Constructor that initializes a JwtResponse with the provided token.
     * 
     * @param token The JWT authentication token
     */
    public JwtResponse(String token) {
        this.token = token;
    }
    
    /**
     * Gets the JWT token.
     * 
     * @return The JWT token string
     */
    public String getToken() {
        return token;
    }
    
    /**
     * Gets the token type (Bearer by default).
     * Could be useful for OAuth2 implementations, but not essential for this test.
     * 
     * @return The token type string
     */
    public String getType() {
        return type;
    }
} 