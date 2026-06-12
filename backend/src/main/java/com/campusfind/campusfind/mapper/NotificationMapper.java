package com.campusfind.campusfind.mapper;

import com.campusfind.campusfind.dto.notification.NotificationResponse;
import com.campusfind.campusfind.model.Notification;

public class NotificationMapper {
    public static NotificationResponse toResponse(Notification n) {
        return NotificationResponse.builder()
                .id(n.getId())
                .message(n.getMessage())
                .read(n.isRead())
                .createdAt(n.getCreatedAt())
                .build();
    }
}
