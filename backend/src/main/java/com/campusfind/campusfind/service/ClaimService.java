package com.campusfind.campusfind.service;

import com.campusfind.campusfind.dto.claim.*;
import com.campusfind.campusfind.exception.BadRequestException;
import com.campusfind.campusfind.exception.ResourceNotFoundException;
import com.campusfind.campusfind.mapper.ClaimMapper;
import com.campusfind.campusfind.model.*;
import com.campusfind.campusfind.repository.ClaimRepository;
import com.campusfind.campusfind.repository.ItemRepository;
import com.campusfind.campusfind.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClaimService {
    private final ClaimRepository claimRepository;
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final CurrentUserService currentUserService;
    private final NotificationService notificationService;

    public ClaimResponse create(ClaimRequest request) {
        User user = currentUserService.getCurrentUser();
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new ResourceNotFoundException("Item not found"));

        if (item.getApprovalStatus() != ApprovalStatus.APPROVED) {
            throw new BadRequestException("You can claim only approved posts");
        }
        if (item.getStatus() != ItemStatus.ACTIVE) {
            throw new BadRequestException("This item is not active for claiming");
        }
        if (item.getUserId().equals(user.getId())) {
            throw new BadRequestException("You cannot claim your own item post");
        }
        if (claimRepository.existsByItemIdAndClaimerIdAndStatusIn(item.getId(), user.getId(), List.of(ClaimStatus.PENDING, ClaimStatus.APPROVED))) {
            throw new BadRequestException("You already have an active claim for this item");
        }

        Claim claim = Claim.builder()
                .itemId(item.getId())
                .claimerId(user.getId())
                .ownerId(item.getUserId())
                .message(request.getMessage())
                .proof(request.getProof())
                .status(ClaimStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        Claim saved = claimRepository.save(claim);
        notificationService.create(item.getUserId(), user.getName() + " sent a claim request for " + item.getItemName());
        return toResponse(saved);
    }

    public List<ClaimResponse> myClaims() {
        String userId = currentUserService.getCurrentUser().getId();
        return claimRepository.findByClaimerIdOrderByCreatedAtDesc(userId).stream().map(this::toResponse).toList();
    }

    public List<ClaimResponse> receivedClaims() {
        String userId = currentUserService.getCurrentUser().getId();
        return claimRepository.findByOwnerIdOrderByCreatedAtDesc(userId).stream().map(this::toResponse).toList();
    }

    public ClaimResponse approve(String id) {
        return updateStatus(id, ClaimStatus.APPROVED);
    }

    public ClaimResponse reject(String id) {
        return updateStatus(id, ClaimStatus.REJECTED);
    }

    private ClaimResponse updateStatus(String id, ClaimStatus status) {
        User user = currentUserService.getCurrentUser();
        Claim claim = claimRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Claim not found"));
        if (!claim.getOwnerId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new BadRequestException("You can update only claims received on your posts");
        }
        if (claim.getStatus() != ClaimStatus.PENDING) {
            throw new BadRequestException("Only pending claims can be updated");
        }

        claim.setStatus(status);
        claim.setUpdatedAt(LocalDateTime.now());
        claimRepository.save(claim);

        Item item = itemRepository.findById(claim.getItemId()).orElse(null);
        if (status == ClaimStatus.APPROVED && item != null) {
            item.setStatus(ItemStatus.CLAIMED);
            item.setUpdatedAt(LocalDateTime.now());
            itemRepository.save(item);
            notificationService.create(claim.getClaimerId(), "Your claim for " + item.getItemName() + " was approved. You can now view contact details.");
        } else if (status == ClaimStatus.REJECTED && item != null) {
            notificationService.create(claim.getClaimerId(), "Your claim for " + item.getItemName() + " was rejected");
        }
        return toResponse(claim);
    }

    private ClaimResponse toResponse(Claim claim) {
        User current = currentUserService.getCurrentUser();
        Item item = itemRepository.findById(claim.getItemId()).orElse(null);
        User claimer = userRepository.findById(claim.getClaimerId()).orElse(null);
        boolean canViewContact = current.getRole() == Role.ADMIN || current.getId().equals(claim.getClaimerId()) || current.getId().equals(claim.getOwnerId());
        return ClaimMapper.toResponse(claim, item, claimer, canViewContact);
    }
}
