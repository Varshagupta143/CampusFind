package com.campusfind.campusfind.mapper;

import com.campusfind.campusfind.dto.claim.ClaimResponse;
import com.campusfind.campusfind.model.Claim;
import com.campusfind.campusfind.model.ClaimStatus;
import com.campusfind.campusfind.model.Item;
import com.campusfind.campusfind.model.User;

public class ClaimMapper {
    public static ClaimResponse toResponse(Claim claim, Item item, User claimer, boolean canViewApprovedContact) {
        if (claim == null) return null;
        return ClaimResponse.builder()
                .id(claim.getId())
                .itemId(claim.getItemId())
                .itemName(item != null ? item.getItemName() : null)
                .claimerId(claim.getClaimerId())
                .claimerName(claimer != null ? claimer.getName() : null)
                .ownerId(claim.getOwnerId())
                .message(claim.getMessage())
                .proof(claim.getProof())
                .status(claim.getStatus())
                .createdAt(claim.getCreatedAt())
                .approvedContactInfo(canViewApprovedContact && claim.getStatus() == ClaimStatus.APPROVED && item != null ? item.getContactInfo() : null)
                .build();
    }
}
