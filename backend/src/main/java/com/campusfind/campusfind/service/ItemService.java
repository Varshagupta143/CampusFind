package com.campusfind.campusfind.service;

import com.campusfind.campusfind.dto.item.*;
import com.campusfind.campusfind.exception.BadRequestException;
import com.campusfind.campusfind.exception.ResourceNotFoundException;
import com.campusfind.campusfind.mapper.ItemMapper;
import com.campusfind.campusfind.model.*;
import com.campusfind.campusfind.repository.ClaimRepository;
import com.campusfind.campusfind.repository.ItemRepository;
import com.campusfind.campusfind.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final ClaimRepository claimRepository;
    private final CurrentUserService currentUserService;
    private final FileStorageService fileStorageService;

    public ItemResponse create(ItemRequest request, MultipartFile image) {
        User user = currentUserService.getCurrentUser();
        String imageUrl = fileStorageService.saveImage(image);
        request.setImageUrl(imageUrl);
        Item item = ItemMapper.toEntity(request, user.getId());
        return toResponse(itemRepository.save(item));
    }

    public List<ItemResponse> getAll(String type, String category, String location, String search) {
        User user = currentUserService.getCurrentUser();
        return itemRepository.findAll().stream()
                .filter(i -> user.getRole() == Role.ADMIN || i.getApprovalStatus() == ApprovalStatus.APPROVED || i.getUserId().equals(user.getId()))
                .filter(i -> type == null || type.isBlank() || i.getType().name().equalsIgnoreCase(type))
                .filter(i -> category == null || category.isBlank() || i.getCategory().equalsIgnoreCase(category))
                .filter(i -> location == null || location.isBlank() || i.getLocation().toLowerCase().contains(location.toLowerCase()))
                .filter(i -> search == null || search.isBlank() || contains(i.getItemName(), search) || contains(i.getDescription(), search))
                .sorted(Comparator.comparing(Item::getCreatedAt).reversed())
                .map(this::toResponse)
                .toList();
    }

    private boolean contains(String value, String search) {
        return value != null && value.toLowerCase().contains(search.toLowerCase());
    }

    public ItemResponse getById(String id) {
        Item item = findItem(id);
        User user = currentUserService.getCurrentUser();
        if (item.getApprovalStatus() != ApprovalStatus.APPROVED && !item.getUserId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new BadRequestException("This post is not approved yet");
        }
        return toResponse(item);
    }

    public List<ItemResponse> myPosts() {
        User user = currentUserService.getCurrentUser();
        return itemRepository.findByUserIdOrderByCreatedAtDesc(user.getId()).stream()
                .map(this::toResponse)
                .toList();
    }

    public ItemResponse update(String id, ItemRequest request) {
        User user = currentUserService.getCurrentUser();
        Item item = findItem(id);
        if (!item.getUserId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new BadRequestException("You can update only your own post");
        }
        ItemMapper.updateEntity(item, request);
        if (user.getRole() != Role.ADMIN) {
            item.setApprovalStatus(ApprovalStatus.PENDING);
        }
        return toResponse(itemRepository.save(item));
    }

    public void delete(String id) {
        User user = currentUserService.getCurrentUser();
        Item item = findItem(id);
        if (!item.getUserId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new BadRequestException("You can delete only your own post");
        }
        itemRepository.delete(item);
    }

    public ItemResponse updateStatus(String id, ItemStatus status) {
        User user = currentUserService.getCurrentUser();
        Item item = findItem(id);
        if (!item.getUserId().equals(user.getId()) && user.getRole() != Role.ADMIN) {
            throw new BadRequestException("You can update only your own post");
        }
        item.setStatus(status);
        item.setUpdatedAt(LocalDateTime.now());
        return toResponse(itemRepository.save(item));
    }

    public Item findItem(String id) {
        return itemRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Item not found"));
    }

    public ItemResponse toResponse(Item item) {
        User currentUser = currentUserService.getCurrentUser();
        String name = userRepository.findById(item.getUserId()).map(User::getName).orElse("Unknown");
        boolean canViewContact = canViewContact(item, currentUser);
        return ItemMapper.toResponse(item, name, canViewContact);
    }

    private boolean canViewContact(Item item, User currentUser) {
        if (currentUser == null) return false;
        if (currentUser.getRole() == Role.ADMIN) return true;
        if (item.getUserId().equals(currentUser.getId())) return true;
        return claimRepository.existsByItemIdAndClaimerIdAndStatus(item.getId(), currentUser.getId(), ClaimStatus.APPROVED);
    }
}
