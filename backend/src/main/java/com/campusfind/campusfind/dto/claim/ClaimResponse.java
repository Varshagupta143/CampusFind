package com.campusfind.campusfind.dto.claim;

import com.campusfind.campusfind.model.ClaimStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClaimResponse {

    private String id;

    private String itemId;
    private String itemName;

    private String claimerId;
    private String claimerName;

    // Visible only after claim is approved
    private String claimerEmail;
    private String claimerPhone;

    private String ownerId;
    private String ownerName;

    // Visible only after claim is approved
    private String ownerEmail;
    private String ownerPhone;

    private String message;
    private String proof;

    private ClaimStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Extra contact info written while posting item
    // Visible only after the claim is approved
    private String approvedContactInfo;
}
