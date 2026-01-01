package com.za.smartfreelance.auth_service.service;

import com.za.smartfreelance.auth_service.dto.AuthResponse;
import com.za.smartfreelance.auth_service.dto.LoginRequest;
import com.za.smartfreelance.auth_service.dto.RegisterRequest;

public interface IAuthService {
    AuthResponse register(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}