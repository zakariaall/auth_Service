package com.za.smartfreelance.auth_service;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtService jwtService;
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        // Initialsation dial l-service m3a chi Secret Key dyal l-test
        jwtService = new JwtService(); 
        userDetails = new User("zakaria_freelancer", "password123", new ArrayList<>());
    }

    @Test
    void shouldGenerateValidToken() {
        // 1. Generation dial token
        String token = jwtService.generateToken(userDetails);
        
        // 2. Vérification
        assertNotNull(token);
        assertEquals("zakaria_freelancer", jwtService.extractUsername(token));
    }

    @Test
    void shouldValidateTokenCorrectly() {
        String token = jwtService.generateToken(userDetails);
        assertTrue(jwtService.isTokenValid(token, userDetails));
    }
}