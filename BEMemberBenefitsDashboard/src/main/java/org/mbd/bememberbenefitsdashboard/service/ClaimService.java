package org.mbd.bememberbenefitsdashboard.service;

import org.mbd.bememberbenefitsdashboard.dto.ClaimDetailDTO;
import org.mbd.bememberbenefitsdashboard.dto.ClaimLineDTO;
import org.mbd.bememberbenefitsdashboard.dto.ClaimStatusEventDTO;
import org.mbd.bememberbenefitsdashboard.entity.*;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;
import org.mbd.bememberbenefitsdashboard.repository.ClaimRepository;
import org.mbd.bememberbenefitsdashboard.repository.UserRepository;
import org.springframework.data.domain.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ClaimService {

    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;

    public ClaimService(ClaimRepository claimRepository, UserRepository userRepository) {
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
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

        List<ClaimStatusEventDTO> statusEventDTOs = getClaimStatusDTOs(claim); //Another helper function
        dto.setClaimStatusEvents(statusEventDTOs);

        return dto;
    }

    public Page<ClaimDetailDTO> getAllClaimsWithFilters(
            Jwt jwt,
            List<ClaimStatus> status,
            LocalDate startDate,
            LocalDate endDate,
            String provider,
            String claimNumber,
            Pageable pageable) {

        String sub = jwt.getClaim("sub");
        User user = userRepository.getByAuthSub(sub);
        Member member = user.getMember();

        List<Claim> allClaims = claimRepository.findAllByMemberOrderByReceivedDateDesc(member);

        // Applying all filters
        List<Claim> filtered = allClaims.stream() //Go through all claims and return only ones that meet the filter conditions
                .filter(c -> status == null || status.contains(c.getStatus()))
                .filter(c -> startDate == null || !c.getServiceStartDate().isBefore(startDate))
                .filter(c -> endDate == null || !c.getServiceEndDate().isAfter(endDate))
                .filter(c -> provider == null ||
                        c.getProvider().getName().toLowerCase().contains(provider.toLowerCase()))
                .filter(c -> claimNumber == null ||
                        c.getClaimNumber().equalsIgnoreCase(claimNumber))
                .toList();

        // Paginate manually
        int start = (int) pageable.getOffset(); //"How many rows do we skip at start". Depending on what page we are on. Gets index of the starting row
        int end = Math.min(start + pageable.getPageSize(), filtered.size()); //Compare results per page with total results. Grab min.
        if (start >= filtered.size()) { // Used when results size changes and already on next page.
            return new PageImpl<>(List.of(), pageable, filtered.size()); //return empty page
        }
        List<Claim> pagedResults = filtered.subList(start, end); //Creates sub list for results per page

        // Convert the filtered and paged results into DTOs
        List<ClaimDetailDTO> dtoList = new ArrayList<>();
        for (Claim c : pagedResults) {
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

        // Return Page
        return new PageImpl<>(dtoList, pageable, filtered.size()); //Allows us to build page with those parameters
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

    //Own function for Claim staus events
    private static List<ClaimStatusEventDTO> getClaimStatusDTOs(Claim claim) {
        List<ClaimStatusEventDTO> statusDTOs = new ArrayList<>();
        for (ClaimStatusEvent event : claim.getStatusHistory()) {
            ClaimStatusEventDTO dto = new ClaimStatusEventDTO();
            dto.setStatus(event.getStatus());
            dto.setOccurredAt(event.getOccurredAt());
            statusDTOs.add(dto);
        }
        return statusDTOs;
    }


}
