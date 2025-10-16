package org.mbd.bememberbenefitsdashboard.controller;

import org.mbd.bememberbenefitsdashboard.dto.ClaimDTO;
import org.mbd.bememberbenefitsdashboard.dto.ClaimDetailDTO;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;
import org.mbd.bememberbenefitsdashboard.service.ClaimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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

    @GetMapping("/allclaims")
    public Page<ClaimDetailDTO> getAllClaims(
            @RequestParam(required = false) List<ClaimStatus> status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String claimNumber,
            Pageable pageable
    ) {
        return claimService.getAllClaimsWithFilters(status, startDate, endDate, provider, claimNumber, pageable);
    }

}
