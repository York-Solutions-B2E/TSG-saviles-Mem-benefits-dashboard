package org.mbd.bememberbenefitsdashboard.dto;

import lombok.Getter;
import lombok.Setter;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;
import java.time.OffsetDateTime;

@Getter
@Setter
public class ClaimStatusEventDTO {
    private ClaimStatus status;
    private OffsetDateTime occurredAt;

}
