package org.mbd.bememberbenefitsdashboard.entity;

import jakarta.persistence.*;
import lombok.*;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "claim_status_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClaimStatusEvent {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "claim_id", nullable = false)
    private Claim claim;

    @Enumerated(EnumType.STRING)
    private ClaimStatus status;

    private OffsetDateTime occurredAt;
    private String note;
}
