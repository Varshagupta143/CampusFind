package com.campusfind.campusfind.dto.item;

import com.campusfind.campusfind.model.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ItemResponse {
    private String id;
    private String userId;
    private String postedByName;
    private String itemName;
    private String category;
    private ItemType type;
    private String description;
    private String location;
    private LocalDate itemDate;
    private String imageUrl;

    // Hidden unless the logged-in user owns the post, is ADMIN, or has an approved claim.
    private String contactInfo;
    private boolean contactVisible;

    private String submittedTo;
    private ItemStatus status;
    private ApprovalStatus approvalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
