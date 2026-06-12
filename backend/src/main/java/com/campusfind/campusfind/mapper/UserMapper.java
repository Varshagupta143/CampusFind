package com.campusfind.campusfind.mapper;

import com.campusfind.campusfind.dto.user.UserResponse;
import com.campusfind.campusfind.model.User;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .rollNumber(user.getRollNumber())
                .branch(user.getBranch())
                .year(user.getYear())
                .phone(user.getPhone())
                .role(user.getRole())
                .build();
    }
}
