package com.za.smartfreelance.auth_service.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;


}
