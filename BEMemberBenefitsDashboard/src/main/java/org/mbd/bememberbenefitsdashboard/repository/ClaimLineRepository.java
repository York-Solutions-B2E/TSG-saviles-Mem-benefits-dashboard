package org.mbd.bememberbenefitsdashboard.repository;

import org.mbd.bememberbenefitsdashboard.entity.ClaimLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ClaimLineRepository extends JpaRepository<ClaimLine, UUID> {
}
