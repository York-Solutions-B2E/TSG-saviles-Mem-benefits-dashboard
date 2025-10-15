package org.mbd.bememberbenefitsdashboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mbd.bememberbenefitsdashboard.enums.AccumulatorType;

import java.math.BigDecimal;

@NoArgsConstructor
@Getter
@Setter
public class AccumulatorDTO {
    private AccumulatorType accumulatorType;
    private BigDecimal limitAmount;
    private BigDecimal usedAmount;

    public AccumulatorDTO(AccumulatorType accumulatorType, BigDecimal limitAmount, BigDecimal usedAmount) {
        this.accumulatorType = accumulatorType;
        this.limitAmount = limitAmount;
        this.usedAmount = usedAmount;
    }
}
