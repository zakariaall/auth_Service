package com.za.smartfreelance.auth_service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
        logger.debug("JwtAuthenticationFilter processing request: {} {}", request.getMethod(), request.getRequestURI());
        
        // Ignorer le filtre pour l'endpoint /validate
        if (request.getRequestURI().equals("/api/auth/validate")) {
            logger.debug("Skipping JWT filter for /validate endpoint");
            filterChain.doFilter(request, response);
            return;
        }
        
        String header = request.getHeader("Authorization");
        logger.debug("Authorization header: {}", header != null ? header.substring(0, Math.min(20, header.length())) + "..." : "null");
        
        String token = null;
        String username = null;

        if (header != null && header.startsWith("Bearer ")) {
            token = header.substring(7);
            try {
                username = jwtUtil.getUsernameFromToken(token);
                logger.debug("Extracted username from token: {}", username);
            } catch (Exception e) {
                logger.warn("Error extracting username from token: {}", e.getMessage());
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                logger.debug("Loading user details for username: {}", username);
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                logger.debug("User details loaded successfully for: {}", username);
                
                if (jwtUtil.validateToken(token)) {
                    logger.debug("Token is valid, setting authentication for user: {}", username);
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    logger.debug("Authentication set successfully for user: {}", username);
                } else {
                    logger.warn("Token validation failed for user: {}", username);
                }
            } catch (Exception e) {
                logger.error("Error loading user details for username {}: {}", username, e.getMessage());
                // Si l'utilisateur n'existe pas dans UserDetailsService, on continue
                // L'endpoint /validate gérera la validation du token
            }
        } else {
            if (username == null) {
                logger.debug("No username extracted from token");
            } else {
                logger.debug("Authentication already exists for user: {}", username);
            }
        }
        
        filterChain.doFilter(request, response);
    }
} 