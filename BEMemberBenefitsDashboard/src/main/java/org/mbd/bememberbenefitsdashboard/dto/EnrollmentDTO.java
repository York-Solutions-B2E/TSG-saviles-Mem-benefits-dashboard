package org.mbd.bememberbenefitsdashboard.dto;

import lombok.Getter;
import lombok.Setter;
import org.mbd.bememberbenefitsdashboard.entity.Plan;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@Getter
@Setter
public class EnrollmentDTO {
    private Plan plan;
    private LocalDate coverageStart;
    private LocalDate coverageEnd;

    public EnrollmentDTO(Plan plan, LocalDate coverageStart, LocalDate coverageEnd) {
        this.plan = plan;
        this.coverageStart = coverageStart;
        this.coverageEnd = coverageEnd;
    }
}
