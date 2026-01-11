package org.example.bank_system.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bank_system.dto.request.LoginRequest;
import org.example.bank_system.dto.request.RegisterRequest;
import org.example.bank_system.dto.request.VerifyCodeRequest;
import org.example.bank_system.dto.response.SuccessResponse;
import org.example.bank_system.dto.response.TokenResponse;
import org.example.bank_system.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        TokenResponse login = authService.login(request);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody VerifyCodeRequest request) {
        authService.verifyCode(request);
        return ResponseEntity.ok(SuccessResponse.ok("User verified successfully"));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        String code = authService.registration(request);
        return ResponseEntity.ok(SuccessResponse.ok("User registered successfully: " + code));
    }
}