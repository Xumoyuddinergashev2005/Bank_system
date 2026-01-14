package org.example.bank_system.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bank_system.dto.request.LoginRequest;
import org.example.bank_system.dto.request.RegisterRequest;
import org.example.bank_system.dto.request.VerifyCodeRequest;
import org.example.bank_system.dto.response.SuccessResponse;
import org.example.bank_system.dto.response.TokenResponse;
import org.example.bank_system.entity.user.User;
import org.example.bank_system.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/user/profile/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "This api only for admins")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id)
     {
        return ResponseEntity.ok(authService.getUserProfile(id));
    }
}