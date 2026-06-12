package com.campusfind.campusfind.controller;

import com.campusfind.campusfind.dto.item.*;
import com.campusfind.campusfind.model.ItemStatus;
import com.campusfind.campusfind.model.ItemType;
import com.campusfind.campusfind.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ItemResponse create(@RequestParam String itemName,
                               @RequestParam String category,
                               @RequestParam ItemType type,
                               @RequestParam(required = false) String description,
                               @RequestParam String location,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate itemDate,
                               @RequestParam(required = false) String contactInfo,
                               @RequestParam(required = false) String submittedTo,
                               @RequestParam(required = false) MultipartFile image) {
        ItemRequest request = new ItemRequest();
        request.setItemName(itemName);
        request.setCategory(category);
        request.setType(type);
        request.setDescription(description);
        request.setLocation(location);
        request.setItemDate(itemDate);
        request.setContactInfo(contactInfo);
        request.setSubmittedTo(submittedTo);
        return itemService.create(request, image);
    }

    @GetMapping
    public List<ItemResponse> getAll(@RequestParam(required = false) String type,
                                     @RequestParam(required = false) String category,
                                     @RequestParam(required = false) String location,
                                     @RequestParam(required = false) String search) {
        return itemService.getAll(type, category, location, search);
    }

    @GetMapping("/{id}")
    public ItemResponse getById(@PathVariable String id) {
        return itemService.getById(id);
    }

    @GetMapping("/my-posts")
    public List<ItemResponse> myPosts() {
        return itemService.myPosts();
    }

    @PutMapping("/{id}")
    public ItemResponse update(@PathVariable String id, @Valid @RequestBody ItemRequest request) {
        return itemService.update(id, request);
    }

    @PutMapping("/{id}/status")
    public ItemResponse updateStatus(@PathVariable String id, @RequestBody Map<String, String> body) {
        return itemService.updateStatus(id, ItemStatus.valueOf(body.get("status")));
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable String id) {
        itemService.delete(id);
        return Map.of("message", "Item deleted successfully");
    }
}
