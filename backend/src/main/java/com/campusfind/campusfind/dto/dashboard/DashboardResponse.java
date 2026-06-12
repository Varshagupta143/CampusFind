package com.campusfind.campusfind.dto.dashboard;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardResponse {
    private long totalLostItems;
    private long totalFoundItems;
    private long returnedItems;
    private long myPosts;
    private long pendingClaimsReceived;
    private long myClaims;
}
