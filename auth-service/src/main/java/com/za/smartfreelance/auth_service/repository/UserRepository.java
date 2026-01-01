package com.za.smartfreelance.auth_service.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;

import com.za.smartfreelance.auth_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
