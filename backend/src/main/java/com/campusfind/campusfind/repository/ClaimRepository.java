package com.campusfind.campusfind.repository;

import com.campusfind.campusfind.model.Claim;
import com.campusfind.campusfind.model.ClaimStatus;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ClaimRepository extends MongoRepository<Claim, String> {
    List<Claim> findByClaimerIdOrderByCreatedAtDesc(String claimerId);
    List<Claim> findByOwnerIdOrderByCreatedAtDesc(String ownerId);
    long countByOwnerIdAndStatus(String ownerId, ClaimStatus status);
    long countByClaimerId(String claimerId);
    boolean existsByItemIdAndClaimerIdAndStatus(String itemId, String claimerId, ClaimStatus status);
    boolean existsByItemIdAndClaimerIdAndStatusIn(String itemId, String claimerId, List<ClaimStatus> statuses);
}
