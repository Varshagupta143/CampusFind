package com.campusfind.campusfind.service;

import com.campusfind.campusfind.dto.notification.NotificationResponse;
import com.campusfind.campusfind.mapper.NotificationMapper;
import com.campusfind.campusfind.model.Notification;
import com.campusfind.campusfind.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CurrentUserService currentUserService;

    public void create(String userId, String message) {
        notificationRepository.save(Notification.builder()
                .userId(userId)
                .message(message)
                .read(false)
                .createdAt(LocalDateTime.now())
                .build());
    }

    public List<NotificationResponse> myNotifications() {
        String userId = currentUserService.getCurrentUser().getId();
        return notificationRepository.findByUserIdOrderByCreatedAtDesc(userId)
                .stream().map(NotificationMapper::toResponse).toList();
    }
}
