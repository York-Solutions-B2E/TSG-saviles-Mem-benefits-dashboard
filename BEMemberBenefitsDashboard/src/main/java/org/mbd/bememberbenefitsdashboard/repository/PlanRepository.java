package org.mbd.bememberbenefitsdashboard.repository;

import org.mbd.bememberbenefitsdashboard.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PlanRepository extends JpaRepository<Plan, UUID> {
}
