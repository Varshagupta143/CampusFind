package com.campusfind.campusfind.mapper;

import com.campusfind.campusfind.dto.item.ItemRequest;
import com.campusfind.campusfind.dto.item.ItemResponse;
import com.campusfind.campusfind.model.*;

import java.time.LocalDateTime;

public class ItemMapper {
    public static Item toEntity(ItemRequest request, String userId) {
        LocalDateTime now = LocalDateTime.now();
        return Item.builder()
                .userId(userId)
                .itemName(request.getItemName())
                .category(request.getCategory())
                .type(request.getType())
                .description(request.getDescription())
                .location(request.getLocation())
                .itemDate(request.getItemDate())
                .imageUrl(request.getImageUrl())
                .contactInfo(request.getContactInfo())
                .submittedTo(request.getSubmittedTo())
                .status(ItemStatus.ACTIVE)
                .approvalStatus(ApprovalStatus.PENDING)
                .createdAt(now)
                .updatedAt(now)
                .build();
    }

    public static void updateEntity(Item item, ItemRequest request) {
        item.setItemName(request.getItemName());
        item.setCategory(request.getCategory());
        item.setType(request.getType());
        item.setDescription(request.getDescription());
        item.setLocation(request.getLocation());
        item.setItemDate(request.getItemDate());

        // Preserve old image if no new image/imageUrl is provided during update
        if (request.getImageUrl() != null && !request.getImageUrl().isBlank()) {
            item.setImageUrl(request.getImageUrl());
        }

        item.setContactInfo(request.getContactInfo());
        item.setSubmittedTo(request.getSubmittedTo());
        item.setUpdatedAt(LocalDateTime.now());
    }

    public static ItemResponse toResponse(Item item, String postedByName, boolean canViewContact) {
        if (item == null) return null;
        return ItemResponse.builder()
                .id(item.getId())
                .userId(item.getUserId())
                .postedByName(postedByName)
                .itemName(item.getItemName())
                .category(item.getCategory())
                .type(item.getType())
                .description(item.getDescription())
                .location(item.getLocation())
                .itemDate(item.getItemDate())
                .imageUrl(item.getImageUrl())
                .contactInfo(canViewContact ? item.getContactInfo() : null)
                .contactVisible(canViewContact)
                .submittedTo(item.getSubmittedTo())
                .status(item.getStatus())
                .approvalStatus(item.getApprovalStatus())
                .createdAt(item.getCreatedAt())
                .updatedAt(item.getUpdatedAt())
                .build();
    }
}
