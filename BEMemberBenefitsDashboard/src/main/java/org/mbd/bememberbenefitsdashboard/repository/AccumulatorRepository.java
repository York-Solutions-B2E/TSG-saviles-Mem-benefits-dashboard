package org.mbd.bememberbenefitsdashboard.repository;

import org.mbd.bememberbenefitsdashboard.entity.Accumulator;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AccumulatorRepository extends JpaRepository<Accumulator, UUID> {
}
