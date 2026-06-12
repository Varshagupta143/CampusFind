package com.campusfind.campusfind.controller;

import com.campusfind.campusfind.dto.notification.NotificationResponse;
import com.campusfind.campusfind.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    public List<NotificationResponse> myNotifications() {
        return notificationService.myNotifications();
    }
}
