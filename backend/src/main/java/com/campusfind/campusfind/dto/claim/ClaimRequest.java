package com.campusfind.campusfind.dto.claim;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClaimRequest {
    @NotBlank private String itemId;
    @NotBlank private String message;
    private String proof;
}
