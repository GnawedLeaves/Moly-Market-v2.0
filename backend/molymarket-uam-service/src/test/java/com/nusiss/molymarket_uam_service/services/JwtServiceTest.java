package com.nusiss.molymarket_uam_service.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Base64;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

public class JwtServiceTest {

    private JwtService jwtService;
    private String secret = Base64.getEncoder().encodeToString("357638792F423F4528482B4D6251655468576D5A7134743777217A25432A462D".getBytes());

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secret", secret);
    }

    @Test
    void testGenerateToken() {
        String token = jwtService.generateToken("testuser");
        assertNotNull(token);
    }

    @Test
    void testExtractUsername() {
        String token = jwtService.generateToken("testuser");
        String username = jwtService.extractUsername(token);
        assertEquals("testuser", username);
    }

    @Test
    void testValidateToken() {
        String token = jwtService.generateToken("testuser");
        UserDetails userDetails = new User("testuser", "password", Collections.emptyList());
        assertTrue(jwtService.validateToken(token, userDetails));
    }
}
