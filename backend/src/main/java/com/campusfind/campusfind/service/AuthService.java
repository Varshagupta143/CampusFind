package com.campusfind.campusfind.service;

import com.campusfind.campusfind.dto.auth.*;
import com.campusfind.campusfind.exception.BadRequestException;
import com.campusfind.campusfind.mapper.UserMapper;
import com.campusfind.campusfind.model.Role;
import com.campusfind.campusfind.model.User;
import com.campusfind.campusfind.repository.UserRepository;
import com.campusfind.campusfind.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email already registered");
        }
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .rollNumber(request.getRollNumber())
                .branch(request.getBranch())
                .year(request.getYear())
                .phone(request.getPhone())
                .role(Role.STUDENT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return AuthResponse.builder()
                .token(jwtUtil.generateToken(user.getEmail()))
                .user(UserMapper.toResponse(user))
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadRequestException("Invalid credentials"));
        return AuthResponse.builder()
                .token(jwtUtil.generateToken(user.getEmail()))
                .user(UserMapper.toResponse(user))
                .build();
    }
}
