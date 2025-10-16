package org.mbd.bememberbenefitsdashboard.controller;

import org.mbd.bememberbenefitsdashboard.dto.ClaimDetailDTO;
import org.mbd.bememberbenefitsdashboard.service.ClaimService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {
    private final ClaimService claimService;

    public ClaimController(ClaimService claimService) {
        this.claimService = claimService;
    }

    @GetMapping("/{claimId}")
    public ClaimDetailDTO getClaimDetails(@PathVariable UUID claimId) {
        return claimService.getClaimDetails(claimId);
    }

}
