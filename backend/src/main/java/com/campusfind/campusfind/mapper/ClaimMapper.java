package com.campusfind.campusfind.mapper;

import com.campusfind.campusfind.dto.claim.ClaimResponse;
import com.campusfind.campusfind.model.Claim;
import com.campusfind.campusfind.model.ClaimStatus;
import com.campusfind.campusfind.model.Item;
import com.campusfind.campusfind.model.User;

public class ClaimMapper {

    public static ClaimResponse toResponse(
            Claim claim,
            Item item,
            User claimer,
            User owner,
            boolean canViewApprovedContact
    ) {
        if (claim == null) return null;

        boolean isApproved = claim.getStatus() == ClaimStatus.APPROVED;
        boolean canShowContact = canViewApprovedContact && isApproved;

        return ClaimResponse.builder()
                .id(claim.getId())

                .itemId(claim.getItemId())
                .itemName(item != null ? item.getItemName() : null)

                .claimerId(claim.getClaimerId())
                .claimerName(claimer != null ? claimer.getName() : null)

                // Show claimer contact only after claim is approved
                .claimerEmail(canShowContact && claimer != null ? claimer.getEmail() : null)
                .claimerPhone(canShowContact && claimer != null ? claimer.getPhone() : null)

                .ownerId(claim.getOwnerId())
                .ownerName(owner != null ? owner.getName() : null)

                // Show owner contact only after claim is approved
                .ownerEmail(canShowContact && owner != null ? owner.getEmail() : null)
                .ownerPhone(canShowContact && owner != null ? owner.getPhone() : null)

                .message(claim.getMessage())
                .proof(claim.getProof())
                .status(claim.getStatus())

                .createdAt(claim.getCreatedAt())
                .updatedAt(claim.getUpdatedAt())

                // Contact info written while posting item
                .approvedContactInfo(canShowContact && item != null ? item.getContactInfo() : null)

                .build();
    }
}