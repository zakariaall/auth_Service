package com.za.smartfreelance.auth_service;

import com.za.smartfreelance.auth_service.security.JwtUtil; // 1. Import JwtUtil d-sa7
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class JwtServiceTest {

    private JwtUtil jwtUtil; // 2. Khdem b JwtUtil
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Farsi l-valeurs dial @Value bach may-tla3sh lik NullPointerException
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "dGhpcy1pcy1hLXZlcnktbG9uZy1zZWNyZXQta2V5LWZvci1ocy01MTItYWxnb3JpdGhtLWl0LW11c3QtYmUtNjQtY2hhcmFjdGVycw==");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 3600000);

        userDetails = new User("zakaria_freelancer", "password123", new ArrayList<>());
    }

    @Test
    void shouldGenerateValidToken() {
        String token = jwtUtil.generateToken(userDetails.getUsername()); 
        
        assertNotNull(token);
        assertEquals("zakaria_freelancer", jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void shouldValidateTokenCorrectly() {
        String token = jwtUtil.generateToken(userDetails.getUsername());
        assertTrue(jwtUtil.validateToken(token));
    }
}