package com.za.smartfreelance.auth_service.dto;

import com.za.smartfreelance.auth_service.entity.Role;
import lombok.Data;

@Data

public class RegisterRequest {
    
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;
    private Role role;
}
