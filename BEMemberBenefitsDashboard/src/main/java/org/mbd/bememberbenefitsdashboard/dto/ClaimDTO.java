package org.mbd.bememberbenefitsdashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ClaimDTO {
    private UUID id;
    private String claimNumber;
    private ClaimStatus status;
    private BigDecimal totalMemberResponsibility;

}
