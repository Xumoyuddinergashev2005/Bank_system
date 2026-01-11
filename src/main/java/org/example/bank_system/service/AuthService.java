package org.example.bank_system.service;

import lombok.RequiredArgsConstructor;
import org.example.bank_system.dto.request.LoginRequest;
import org.example.bank_system.dto.request.RegisterRequest;
import org.example.bank_system.dto.request.VerifyCodeRequest;
import org.example.bank_system.dto.response.TokenResponse;
import org.example.bank_system.entity.user.KycStatus;
import org.example.bank_system.entity.user.Role;
import org.example.bank_system.entity.user.User;
import org.example.bank_system.entity.user.UserStatus;
import org.example.bank_system.exception.BadRequestException;
import org.example.bank_system.exception.ConflictException;
import org.example.bank_system.exception.NotFoundException;
import org.example.bank_system.exception.UnauthorizedException;
import org.example.bank_system.repository.UserRepository;
import org.example.bank_system.util.NumberGenerator;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder encoder;
    private final NumberGenerator generator;
    private final JwtService jwtService;
    private final RedisTemplate<String, Long> redisTemplate;

    public String registration(RegisterRequest request) {

        userRepository.findByPhoneNumber(request.phoneNumber())
                .ifPresent(user -> {
                    if (user.getStatus().equals(UserStatus.UNVERIFIED)) {
                        userRepository.delete(user);
                    } else {
                        throw new ConflictException("User already exists");
                    }
                });

        Long code = generator.generateNumber();

        redisTemplate.opsForValue().set(request.phoneNumber(), code, 3, TimeUnit.MINUTES);

        User user = User.builder()
                .phoneNumber(request.phoneNumber())
                .firstName(request.firstName())
                .lastName(request.lastName())
                .password(encoder.encode(request.password()))
                .role(Role.USER)
                .status(UserStatus.UNVERIFIED)
                .is_active(true)
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
        // smsService.sendSms(user.getPhoneNumber(), code);
        return code.toString();
    }

    public void verifyCode(VerifyCodeRequest request) {

        Long cachedCode = redisTemplate.opsForValue().get(request.phoneNumber());

        if (cachedCode == null || !cachedCode.equals(request.code())) {
            throw new BadRequestException("Invalid or expired verification code");
        }

        User user = userRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException("User not found"));

        user.setStatus(UserStatus.ACTIVE);
        user.setKycStatus(KycStatus.VERIFIED);
        userRepository.save(user);
        redisTemplate.delete(request.phoneNumber());
    }

    public TokenResponse login(LoginRequest request) {
        User user = userRepository.findByPhoneNumber(request.phoneNumber())
                .orElseThrow(() -> new NotFoundException("User not found"));

        if (!user.getStatus().equals(UserStatus.ACTIVE)) {
            throw new UnauthorizedException("Account not verified. Please verify your account.");
        }

        if (!encoder.matches(request.password(), user.getPassword())) {
            throw new BadRequestException("Invalid credentials");
        }

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.phoneNumber(),
                        request.password()
                )
        );

        String jwtToken = jwtService.generateToken(user);
        userRepository.save(user);
        return new TokenResponse(jwtToken);
    }

}