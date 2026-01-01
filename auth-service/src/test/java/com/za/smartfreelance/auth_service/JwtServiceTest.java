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
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "Zm9sbG93LW1lLW9uLWdpdGh1Yi16YWthcmlhLWFsbGFmb3Utc21hcnQtZnJlZWxhbmNlLXByb2plY3Q=");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 3600000);

        userDetails = new User("zakaria_freelancer", "password123", new ArrayList<>());
    }

    @Test
    void shouldGenerateValidToken() {
        // 3. JwtUtil dialk k-i-akhod String username, machi UserDetails
        String token = jwtUtil.generateToken(userDetails.getUsername()); 
        
        assertNotNull(token);
        // 4. JwtUtil fih getUsernameFromToken() machi extractUsername()
        assertEquals("zakaria_freelancer", jwtUtil.getUsernameFromToken(token));
    }

    @Test
    void shouldValidateTokenCorrectly() {
        String token = jwtUtil.generateToken(userDetails.getUsername());
        // 5. JwtUtil fih validateToken() machi isTokenValid()
        assertTrue(jwtUtil.validateToken(token));
    }
}