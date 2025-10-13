package org.mbd.bememberbenefitsdashboard.entity;
import jakarta.persistence.*;
import lombok.*;
import org.mbd.bememberbenefitsdashboard.enums.PlanType;
import java.util.UUID;

@Entity
@Table(name = "plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Plan {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private PlanType type;

    private String networkName;
    private Integer planYear;
}

