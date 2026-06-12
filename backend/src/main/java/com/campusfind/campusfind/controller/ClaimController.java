package com.campusfind.campusfind.controller;

import com.campusfind.campusfind.dto.claim.*;
import com.campusfind.campusfind.service.ClaimService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
@RequiredArgsConstructor
public class ClaimController {
    private final ClaimService claimService;

    @PostMapping
    public ClaimResponse create(@Valid @RequestBody ClaimRequest request) {
        return claimService.create(request);
    }

    @GetMapping("/my-claims")
    public List<ClaimResponse> myClaims() {
        return claimService.myClaims();
    }

    @GetMapping("/received")
    public List<ClaimResponse> receivedClaims() {
        return claimService.receivedClaims();
    }

    @PutMapping("/{id}/approve")
    public ClaimResponse approve(@PathVariable String id) {
        return claimService.approve(id);
    }

    @PutMapping("/{id}/reject")
    public ClaimResponse reject(@PathVariable String id) {
        return claimService.reject(id);
    }
}
