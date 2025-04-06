package com.owt.boat_test.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@ExtendWith(MockitoExtension.class)
class JwtProviderTest {

    @Mock
    private Authentication authentication;
    
    @Mock
    private UserDetails userDetails;
    
    @InjectMocks
    private JwtProvider jwtProvider;
    
    // La clé exacte utilisée dans JwtProvider
    private final String SECRET_KEY = "MySecretKeyWhoNeedToBeInTheEnvButItsHereForTheTestAndTheVersioning";
    private int expirationMs = 3600 * 1000; // 1 heure
    
    @Test
    void generateToken_ShouldCreateValidJwtToken() {
        // Arrange
        String username = "testuser";
        
        // Act
        String token = jwtProvider.generateToken(username);
        
        // Assert
        assertNotNull(token);
        assertTrue(token.length() > 0);
        
        // Vérifier que le token peut être décodé
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        
        assertEquals(username, claims.getSubject());
        assertTrue(claims.getExpiration().after(new Date()));
    }
    
    @Test
    void validateToken_WithValidToken_ShouldReturnTrue() {
        // Arrange
        String username = "testuser";
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);
        
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        
        // Act
        boolean isValid = jwtProvider.validateToken(token);
        
        // Assert
        assertTrue(isValid);
    }
    
    @Test
    void validateToken_WithExpiredToken_ShouldReturnFalse() {
        // Arrange
        String username = "testuser";
        Date now = new Date();
        Date expiration = new Date(now.getTime() - 1000); // Déjà expiré
        
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        
        // Act
        boolean isValid = jwtProvider.validateToken(token);
        
        // Assert
        assertFalse(isValid);
    }
    
    @Test
    void validateToken_WithInvalidToken_ShouldReturnFalse() {
        // Arrange
        String invalidToken = "invalid.token.format";
        
        // Act
        boolean isValid = jwtProvider.validateToken(invalidToken);
        
        // Assert
        assertFalse(isValid);
    }
    
    @Test
    void getUsernameFromToken_ShouldReturnCorrectUsername() {
        // Arrange
        String username = "testuser";
        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMs);
        
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
        
        // Act
        String extractedUsername = jwtProvider.getUsernameFromToken(token);
        
        // Assert
        assertEquals(username, extractedUsername);
    }
} 