package com.campusfind.campusfind.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Document(collection = "claims")
public class Claim {
    @Id
    private String id;

    private String itemId;
    private String claimerId;
    private String ownerId;
    private String message;
    private String proof;
    private ClaimStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
