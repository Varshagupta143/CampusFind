package com.campusfind.campusfind.dto.notification;

import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NotificationResponse {
    private String id;
    private String message;
    private boolean read;
    private LocalDateTime createdAt;
}
