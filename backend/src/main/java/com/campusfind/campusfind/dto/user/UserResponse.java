package com.campusfind.campusfind.dto.user;

import com.campusfind.campusfind.model.Role;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String rollNumber;
    private String branch;
    private String year;
    private String phone;
    private Role role;
}
