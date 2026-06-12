package com.campusfind.campusfind.dto.claim;

import com.campusfind.campusfind.model.ClaimStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ClaimResponse {
    private String id;
    private String itemId;
    private String itemName;
    private String claimerId;
    private String claimerName;
    private String ownerId;
    private String message;
    private String proof;
    private ClaimStatus status;
    private LocalDateTime createdAt;

    // Visible only after the claim is approved.
    private String approvedContactInfo;
}
