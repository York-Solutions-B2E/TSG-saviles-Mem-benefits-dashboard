package org.mbd.bememberbenefitsdashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mbd.bememberbenefitsdashboard.entity.ClaimStatusEvent;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ClaimDetailDTO {
    private UUID id;
    private String claimNumber;
    private ClaimStatus status;
    private BigDecimal totalMemberResponsibility;
    private BigDecimal totalBilled;
    private BigDecimal totalPlanPaid;
    private BigDecimal totalAllowed;
    private LocalDate recievedDate;
    private LocalDate serviceStartDate;
    private LocalDate serviceEndDate;
    private String providerName;
    private List<ClaimLineDTO> claimLines;
    private List<ClaimStatusEventDTO> claimStatusEvents;


}
