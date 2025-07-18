package com.example.user_management_service.controller;


import com.example.user_management_service.aspect.Monitored;
import com.example.user_management_service.dto.AuthRequest;
import com.example.user_management_service.dto.RefreshTokenRequest;
import com.example.user_management_service.dto.VerifyRequest;
import com.example.user_management_service.dto.UserDTO;
import com.example.user_management_service.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Monitored
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> addNewUser(@RequestBody UserDTO userDto) {
        try {
            String result = service.saveUser(userDto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> getToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );

            if (authenticate.isAuthenticated()) {
                Map<String, String> tokens = service.generateTokens(authRequest.getUsername());
                return ResponseEntity.ok(tokens);
            } else {
                return ResponseEntity.status(401).body("Invalid credentials");
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verifyUser(@RequestBody VerifyRequest ver) {
        return ResponseEntity.ok(service.verifyUser(ver.getId()));
    }

    @GetMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestParam("token") String token) {
        try {
            service.validateToken(token);
            return ResponseEntity.ok("Token is valid");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid or expired token");
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequest request) {
        try {
            Map<String, String> tokens = service.refreshTokens(request.getRefreshToken());
            return ResponseEntity.ok(tokens);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid refresh token");
        }
    }
}
