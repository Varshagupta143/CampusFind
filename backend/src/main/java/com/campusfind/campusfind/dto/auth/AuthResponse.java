package com.campusfind.campusfind.dto.auth;

import com.campusfind.campusfind.dto.user.UserResponse;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder

public class AuthResponse {

    private String token;
    private UserResponse user;
}
