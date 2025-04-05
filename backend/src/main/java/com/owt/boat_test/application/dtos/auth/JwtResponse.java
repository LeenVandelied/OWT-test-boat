package com.owt.boat_test.application.dtos.auth;

public class JwtResponse {
    private String token;
    private String type = "Bearer";
    
    public JwtResponse(String token) {
        this.token = token;
    }
    
    public String getToken() {
        return token;
    }
    
    // Could be nice for Oauth2, but not really needed for this test
    public String getType() {
        return type;
    }
} 