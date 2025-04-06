package com.owt.boat_test.application.services.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.owt.boat_test.application.dtos.auth.JwtResponse;
import com.owt.boat_test.application.dtos.auth.LoginRequest;
import com.owt.boat_test.infrastructure.security.CustomUserDetailsService;
import com.owt.boat_test.infrastructure.security.JwtProvider;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private AuthService authService;

    private LoginRequest validLoginRequest;

    @BeforeEach
    void setUp() {
        validLoginRequest = new LoginRequest();
        validLoginRequest.setUsername("admin");
        validLoginRequest.setPassword("password");
    }

    @Test
    void authenticate_WithValidCredentials_ShouldReturnJwtResponse() {
        // Arrange
        when(userDetailsService.verifyPassword("password", "admin")).thenReturn(true);
        when(jwtProvider.generateToken("admin")).thenReturn("test-jwt-token");

        // Act
        JwtResponse response = authService.authenticate(validLoginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("test-jwt-token", response.getToken());
        verify(userDetailsService).verifyPassword("password", "admin");
        verify(jwtProvider).generateToken("admin");
    }

    @Test
    void authenticate_WithInvalidCredentials_ShouldThrowException() {
        // Arrange
        when(userDetailsService.verifyPassword("password", "admin")).thenReturn(false);

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> {
            authService.authenticate(validLoginRequest);
        });
        
        assertEquals("Invalid credentials", exception.getMessage());
        verify(userDetailsService).verifyPassword("password", "admin");
        verify(jwtProvider, never()).generateToken(anyString());
    }
} 