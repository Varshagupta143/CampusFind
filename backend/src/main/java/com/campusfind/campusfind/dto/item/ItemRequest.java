package com.campusfind.campusfind.dto.item;

import com.campusfind.campusfind.model.ItemType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class ItemRequest {
    @NotBlank private String itemName;
    @NotBlank private String category;
    @NotNull private ItemType type;
    private String description;
    @NotBlank private String location;
    @NotNull private LocalDate itemDate;
    private String imageUrl;
    private String contactInfo;
    private String submittedTo;
}
