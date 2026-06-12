package com.campusfind.campusfind.controller;

import com.campusfind.campusfind.dto.item.ItemResponse;
import com.campusfind.campusfind.dto.user.UserResponse;
import com.campusfind.campusfind.model.ApprovalStatus;
import com.campusfind.campusfind.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/items/pending")
    public List<ItemResponse> pendingItems() {
        return adminService.pendingItems();
    }

    @PutMapping("/items/{id}/approve")
    public ItemResponse approve(@PathVariable String id) {
        return adminService.setApproval(id, ApprovalStatus.APPROVED);
    }

    @PutMapping("/items/{id}/reject")
    public ItemResponse reject(@PathVariable String id) {
        return adminService.setApproval(id, ApprovalStatus.REJECTED);
    }

    @GetMapping("/users")
    public List<UserResponse> users() {
        return adminService.users();
    }
}
