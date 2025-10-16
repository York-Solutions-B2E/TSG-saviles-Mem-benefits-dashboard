package org.mbd.bememberbenefitsdashboard.service;

import org.mbd.bememberbenefitsdashboard.dto.ClaimDetailDTO;
import org.mbd.bememberbenefitsdashboard.dto.ClaimLineDTO;
import org.mbd.bememberbenefitsdashboard.entity.Claim;
import org.mbd.bememberbenefitsdashboard.entity.ClaimLine;
import org.mbd.bememberbenefitsdashboard.repository.ClaimRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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


        List<ClaimLineDTO> lineDTOs = getClaimLineDTOS(claim);

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
}
