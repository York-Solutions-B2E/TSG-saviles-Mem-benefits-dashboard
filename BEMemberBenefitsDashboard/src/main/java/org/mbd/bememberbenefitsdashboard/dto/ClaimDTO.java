package org.mbd.bememberbenefitsdashboard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;

import java.math.BigDecimal;

@Getter
@Setter
public class ClaimDTO {

    private String claimNumber;
    private ClaimStatus status;
    private BigDecimal totalMemberResponsibility;

}
