package com.campusfind.campusfind.repository;

import com.campusfind.campusfind.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByUserIdOrderByCreatedAtDesc(String userId);
    List<Item> findByTypeOrderByCreatedAtDesc(ItemType type);
    long countByType(ItemType type);
    long countByStatus(ItemStatus status);
    long countByUserId(String userId);
    List<Item> findByApprovalStatusOrderByCreatedAtDesc(ApprovalStatus status);
}
