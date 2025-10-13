package org.mbd.bememberbenefitsdashboard.entity;
import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table(name = "providers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Provider {

    @Id
    @GeneratedValue
    private UUID id;

    private String name;
    private String specialty;

    @Embedded
    private Address address;

    private String phone;
}

