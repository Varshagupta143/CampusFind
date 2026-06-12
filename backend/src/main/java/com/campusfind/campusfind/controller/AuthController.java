package com.campusfind.campusfind.controller;

import com.campusfind.campusfind.dto.auth.AuthResponse;
import com.campusfind.campusfind.dto.auth.LoginRequest;
import com.campusfind.campusfind.dto.auth.RegisterRequest;
import com.campusfind.campusfind.dto.user.UserResponse;
import com.campusfind.campusfind.mapper.UserMapper;
import com.campusfind.campusfind.security.CookieService;
import com.campusfind.campusfind.service.AuthService;
import com.campusfind.campusfind.service.CurrentUserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final CurrentUserService currentUserService;
    private final CookieService cookieService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(
            @Valid @RequestBody RegisterRequest request,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.register(request);

        // Store JWT in HttpOnly cookie
        cookieService.addJwtCookie(response, authResponse.getToken());

        // Do not expose token in response body
        authResponse.setToken(null);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        AuthResponse authResponse = authService.login(request);

        // Store JWT in HttpOnly cookie
        cookieService.addJwtCookie(response, authResponse.getToken());

        // Do not expose token in response body
        authResponse.setToken(null);

        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletResponse response) {
        cookieService.clearJwtCookie(response);
        return ResponseEntity.ok("Logged out successfully");
    }

    @GetMapping("/profile")
    public UserResponse profile() {
        return UserMapper.toResponse(currentUserService.getCurrentUser());
    }
}