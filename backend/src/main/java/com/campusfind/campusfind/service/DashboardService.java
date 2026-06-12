package com.campusfind.campusfind.service;

import com.campusfind.campusfind.dto.dashboard.DashboardResponse;
import com.campusfind.campusfind.model.*;
import com.campusfind.campusfind.repository.ClaimRepository;
import com.campusfind.campusfind.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final ItemRepository itemRepository;
    private final ClaimRepository claimRepository;
    private final CurrentUserService currentUserService;

    public DashboardResponse summary() {
        String userId = currentUserService.getCurrentUser().getId();
        return DashboardResponse.builder()
                .totalLostItems(itemRepository.countByType(ItemType.LOST))
                .totalFoundItems(itemRepository.countByType(ItemType.FOUND))
                .returnedItems(itemRepository.countByStatus(ItemStatus.RETURNED))
                .myPosts(itemRepository.countByUserId(userId))
                .pendingClaimsReceived(claimRepository.countByOwnerIdAndStatus(userId, ClaimStatus.PENDING))
                .myClaims(claimRepository.countByClaimerId(userId))
                .build();
    }
}
