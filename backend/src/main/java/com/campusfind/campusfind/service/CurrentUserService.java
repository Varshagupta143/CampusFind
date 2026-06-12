package com.campusfind.campusfind.service;

import com.campusfind.campusfind.exception.ResourceNotFoundException;
import com.campusfind.campusfind.model.User;
import com.campusfind.campusfind.repository.UserRepository;
import com.campusfind.campusfind.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CurrentUserService {
    private final UserRepository userRepository;

    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof CustomUserDetails details) {
            return details.getUser();
        }
        String email = auth != null ? auth.getName() : null;
        return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Current user not found"));
    }
}
