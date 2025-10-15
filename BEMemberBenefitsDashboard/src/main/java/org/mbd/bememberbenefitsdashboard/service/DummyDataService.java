package org.mbd.bememberbenefitsdashboard.service;

import lombok.RequiredArgsConstructor;
import org.mbd.bememberbenefitsdashboard.entity.Accumulator;
import org.mbd.bememberbenefitsdashboard.entity.Enrollment;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.mbd.bememberbenefitsdashboard.entity.Plan;
import org.mbd.bememberbenefitsdashboard.enums.AccumulatorType;
import org.mbd.bememberbenefitsdashboard.enums.NetworkTier;
import org.mbd.bememberbenefitsdashboard.enums.PlanType;
import org.mbd.bememberbenefitsdashboard.repository.AccumulatorRepository;
import org.mbd.bememberbenefitsdashboard.repository.EnrollmentRepository;
import org.mbd.bememberbenefitsdashboard.repository.MemberRepository;
import org.mbd.bememberbenefitsdashboard.repository.PlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DummyDataService {
    private final PlanRepository planRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AccumulatorRepository accumulatorRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void populateDummyData(Member member) {
        // Check if member already has enrollments -> skip if yes
        boolean hasEnrollment = enrollmentRepository.findByMemberAndActiveTrue(member).isPresent();
        if (hasEnrollment) return;

        // 1. Create Plans (if not exists)
        Plan goldPlan = new Plan(null, "Gold PPO", PlanType.PPO, "Prime Network", 2025);
        Plan silverPlan = new Plan(null, "Silver HMO", PlanType.HMO, "CarePlus Network", 2025);
        Plan bronzePlan = new Plan(null, "Bronze EPO", PlanType.EPO, "HealthFirst Network", 2025);
        planRepository.save(goldPlan);
        planRepository.save(silverPlan);
        planRepository.save(bronzePlan);

        // 2. Create Enrollment for the member with Gold PPO plan
        Enrollment enrollment = new Enrollment();
        enrollment.setMember(member);
        enrollment.setPlan(goldPlan);
        enrollment.setCoverageStart(LocalDate.of(2025, 1, 1));
        enrollment.setCoverageEnd(LocalDate.of(2025, 12, 31));
        enrollment.setActive(true);

        enrollmentRepository.save(enrollment);

        // 3. Create Accumulators linked to the enrollment
        Accumulator deductible = new Accumulator();
        deductible.setEnrollment(enrollment);
        deductible.setType(AccumulatorType.DEDUCTIBLE);
        deductible.setTier(NetworkTier.IN_NETWORK);
        deductible.setLimitAmount(BigDecimal.valueOf(2000.00));
        deductible.setUsedAmount(BigDecimal.valueOf(500.50));

        Accumulator oopMax = new Accumulator();
        oopMax.setEnrollment(enrollment);
        oopMax.setType(AccumulatorType.OOP_MAX);
        oopMax.setTier(NetworkTier.IN_NETWORK);
        oopMax.setLimitAmount(BigDecimal.valueOf(5000.00));
        oopMax.setUsedAmount(BigDecimal.valueOf(2500.50));

        accumulatorRepository.saveAll(Arrays.asList(deductible, oopMax));
    }
}
