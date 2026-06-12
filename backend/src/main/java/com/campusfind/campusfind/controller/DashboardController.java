package com.campusfind.campusfind.controller;

import com.campusfind.campusfind.dto.dashboard.DashboardResponse;
import com.campusfind.campusfind.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping("/summary")
    public DashboardResponse summary() {
        return dashboardService.summary();
    }
}
