package com.za.smartfreelance.auth_service.controller;

import com.za.smartfreelance.auth_service.dto.*;
import com.za.smartfreelance.auth_service.service.IAuthService;
import com.za.smartfreelance.auth_service.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final IAuthService authService;
    private final JwtUtil jwtUtil;

    @Autowired
    public AuthController(IAuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Auth service is working! CORS should be enabled.");
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            String jwtToken = token.substring(7);
            boolean isValid = jwtUtil.validateToken(jwtToken);
            if (isValid) {
                String username = jwtUtil.getUsernameFromToken(jwtToken);
                return ResponseEntity.ok(java.util.Map.of("valid", true, "username", username));
            }
            return ResponseEntity.status(401).body(java.util.Map.of("valid", false));
        }
        return ResponseEntity.badRequest().body(java.util.Map.of("valid", false, "reason", "missing token"));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(400).body(ex.getMessage());
    }
}