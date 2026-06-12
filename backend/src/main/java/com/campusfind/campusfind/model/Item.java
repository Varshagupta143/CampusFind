package com.campusfind.campusfind.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "items")
public class Item {
    @Id
    private String id;

    private String userId;
    private String itemName;
    private String category;
    private ItemType type;
    private String description;
    private String location;
    private LocalDate itemDate;
    private String imageUrl;
    private String contactInfo;
    private String submittedTo;
    private ItemStatus status;
    private ApprovalStatus approvalStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
