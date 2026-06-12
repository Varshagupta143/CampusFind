package com.campusfind.campusfind.service;

import com.campusfind.campusfind.dto.item.ItemResponse;
import com.campusfind.campusfind.dto.user.UserResponse;
import com.campusfind.campusfind.exception.ResourceNotFoundException;
import com.campusfind.campusfind.mapper.UserMapper;
import com.campusfind.campusfind.model.*;
import com.campusfind.campusfind.repository.ItemRepository;
import com.campusfind.campusfind.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ItemService itemService;

    public List<ItemResponse> pendingItems() {
        return itemRepository.findByApprovalStatusOrderByCreatedAtDesc(ApprovalStatus.PENDING)
                .stream().map(itemService::toResponse).toList();
    }

    public ItemResponse setApproval(String id, ApprovalStatus status) {
        Item item = itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found"));
        item.setApprovalStatus(status);
        item.setUpdatedAt(java.time.LocalDateTime.now());
        return itemService.toResponse(itemRepository.save(item));
    }

    public List<UserResponse> users() {
        return userRepository.findAll().stream().map(UserMapper::toResponse).toList();
    }
}
