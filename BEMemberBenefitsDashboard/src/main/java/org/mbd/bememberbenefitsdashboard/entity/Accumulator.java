package org.mbd.bememberbenefitsdashboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.mbd.bememberbenefitsdashboard.enums.AccumulatorType;
import org.mbd.bememberbenefitsdashboard.enums.NetworkTier;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "accumulators")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Accumulator {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "enrollment_id", nullable = false)
    private Enrollment enrollment;

    @Enumerated(EnumType.STRING)
    private AccumulatorType type;

    @Enumerated(EnumType.STRING)
    private NetworkTier tier;

    private BigDecimal limitAmount;
    private BigDecimal usedAmount;
}

