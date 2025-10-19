package org.mbd.bememberbenefitsdashboard.service;

import org.mbd.bememberbenefitsdashboard.dto.ClaimDetailDTO;
import org.mbd.bememberbenefitsdashboard.dto.ClaimLineDTO;
import org.mbd.bememberbenefitsdashboard.entity.Claim;
import org.mbd.bememberbenefitsdashboard.entity.ClaimLine;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;
import org.mbd.bememberbenefitsdashboard.repository.ClaimRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;

    public ClaimService(ClaimRepository claimRepository) {
        this.claimRepository = claimRepository;
    }

    public ClaimDetailDTO getClaimDetails(UUID claimId) {
        Claim claim = claimRepository.findById(claimId)
                .orElseThrow(() -> new RuntimeException("Claim not found"));

        ClaimDetailDTO dto = new ClaimDetailDTO();
        dto.setId(claim.getId());
        dto.setClaimNumber(claim.getClaimNumber());
        dto.setStatus(claim.getStatus());
        dto.setTotalMemberResponsibility(claim.getTotalMemberResponsibility());
        dto.setTotalBilled(claim.getTotalBilled());
        dto.setTotalPlanPaid(claim.getTotalPlanPaid());
        dto.setTotalAllowed(claim.getTotalAllowed());
        dto.setRecievedDate(claim.getReceivedDate());
        dto.setServiceStartDate(claim.getServiceStartDate());
        dto.setServiceEndDate(claim.getServiceEndDate());
        dto.setProviderName(claim.getProvider().getName());

        List<ClaimLineDTO> lineDTOs = getClaimLineDTOS(claim); //Helper function

        dto.setClaimLines(lineDTOs);


        return dto;
    }

    //Own function for claim line DTO
    private static List<ClaimLineDTO> getClaimLineDTOS(Claim claim) {
        List<ClaimLineDTO> lineDTOs = new ArrayList<>();
        for (ClaimLine line : claim.getLines()) {
            ClaimLineDTO lineDTO = new ClaimLineDTO();
            lineDTO.setCptCode(line.getCptCode());
            lineDTO.setDescription(line.getDescription());
            lineDTO.setBilledAmount(line.getBilledAmount());
            lineDTO.setAllowedAmount(line.getAllowedAmount());
            lineDTO.setDeductibleApplied(line.getDeductibleApplied());
            lineDTO.setCopayApplied(line.getCopayApplied());
            lineDTO.setCoinsuranceApplied(line.getCoinsuranceApplied());
            lineDTO.setMemberResponsibility(line.getMemberResponsibility());
            lineDTO.setPlanPaid(line.getPlanPaid());
            lineDTOs.add(lineDTO);
        }
        return lineDTOs;
    }

    public Page<ClaimDetailDTO> getAllClaimsWithFilters(
            List<ClaimStatus> status,
            LocalDate startDate,
            LocalDate endDate,
            String provider,
            String claimNumber,
            Pageable pageable) {

        List<Claim> allClaims = claimRepository.findAllByOrderByReceivedDateDesc();

        // 1. Filter first
        List<Claim> filtered = allClaims.stream()
                .filter(c -> status == null || status.contains(c.getStatus()))
                .filter(c -> startDate == null || !c.getServiceStartDate().isBefore(startDate))
                .filter(c -> endDate == null || !c.getServiceEndDate().isAfter(endDate))
                .filter(c -> provider == null ||
                        c.getProvider().getName().toLowerCase().contains(provider.toLowerCase()))
                .filter(c -> claimNumber == null ||
                        c.getClaimNumber().equalsIgnoreCase(claimNumber))
                .toList();

        // 2. Paginate manually
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), filtered.size());
        if (start >= filtered.size()) {
            return new PageImpl<>(List.of(), pageable, filtered.size());
        }
        List<Claim> paged = filtered.subList(start, end);

        // 3. Convert to DTOs
        List<ClaimDetailDTO> dtoList = new ArrayList<>();
        for (Claim c : paged) {
            ClaimDetailDTO dto = new ClaimDetailDTO();
            dto.setId(c.getId());
            dto.setClaimNumber(c.getClaimNumber());
            dto.setStatus(c.getStatus());
            dto.setTotalMemberResponsibility(c.getTotalMemberResponsibility());
            dto.setProviderName(c.getProvider().getName());
            dto.setServiceStartDate(c.getServiceStartDate());
            dto.setServiceEndDate(c.getServiceEndDate());
            dtoList.add(dto);
        }

        // 4. Return Page
        return new PageImpl<>(dtoList, pageable, filtered.size());
    }


}
