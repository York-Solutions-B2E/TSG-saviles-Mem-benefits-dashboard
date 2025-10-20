package org.mbd.bememberbenefitsdashboard.controller;

import org.mbd.bememberbenefitsdashboard.dto.ClaimDetailDTO;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;
import org.mbd.bememberbenefitsdashboard.service.ClaimService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/claims")
@CrossOrigin(origins = "http://localhost:5173")
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
            @AuthenticationPrincipal Jwt jwt,
            @RequestParam(required = false) List<ClaimStatus> status,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) String provider,
            @RequestParam(required = false) String claimNumber,
            Pageable pageable // interface from spring data. can automatically process the page & size sent by front end
    ) {
        return claimService.getAllClaimsWithFilters(jwt, status, startDate, endDate, provider, claimNumber, pageable);
    }

}
