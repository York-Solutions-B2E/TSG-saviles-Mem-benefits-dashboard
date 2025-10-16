package org.mbd.bememberbenefitsdashboard.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ClaimLineDTO {
    private String cptCode;
    private String description;
    private BigDecimal billedAmount;
    private BigDecimal allowedAmount;
    private BigDecimal deductibleApplied;
    private BigDecimal copayApplied;
    private BigDecimal coinsuranceApplied;
    private BigDecimal memberResponsibility;
    private BigDecimal planPaid;
}
