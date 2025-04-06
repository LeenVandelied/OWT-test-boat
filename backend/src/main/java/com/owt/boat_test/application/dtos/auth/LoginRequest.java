package com.owt.boat_test.application.dtos.auth;

/**
 * Data Transfer Object (DTO) for login requests.
 * Contains the credentials (username and password) submitted by the user during authentication.
 */
public class LoginRequest {
    private String username;
    private String password;
    
    /**
     * Default constructor required for serialization/deserialization.
     */
    public LoginRequest() {
    }
    
    /**
     * Constructor with all fields for creating a complete LoginRequest.
     * 
     * @param username The username for authentication
     * @param password The password for authentication
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username from the login credentials.
     * 
     * @return The username string
     */
    public String getUsername() {
        return username;
    }
    
    /**
     * Sets the username for the login credentials.
     * 
     * @param username The username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * Gets the password from the login credentials.
     * 
     * @return The password string
     */
    public String getPassword() {
        return password;
    }
    
    /**
     * Sets the password for the login credentials.
     * 
     * @param password The password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }
} 