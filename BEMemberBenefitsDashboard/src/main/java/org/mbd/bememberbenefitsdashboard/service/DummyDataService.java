package org.mbd.bememberbenefitsdashboard.service;

import lombok.RequiredArgsConstructor;
import org.mbd.bememberbenefitsdashboard.entity.*;
import org.mbd.bememberbenefitsdashboard.enums.AccumulatorType;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;
import org.mbd.bememberbenefitsdashboard.enums.NetworkTier;
import org.mbd.bememberbenefitsdashboard.enums.PlanType;
import org.mbd.bememberbenefitsdashboard.repository.*;
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
    private final ProviderRepository providerRepository;
    private final ClaimRepository claimRepository;
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

// ✅ 4a. Create or get multiple Providers
        Address addr1 = new Address();
        addr1.setLine1("123 Main St");
        addr1.setCity("Springfield");
        addr1.setState("IL");
        addr1.setPostalCode("62704");

        Provider provider1 = new Provider();
        provider1.setName("Prime Health Clinic");
        provider1.setSpecialty("Primary Care");
        provider1.setAddress(addr1);
        provider1.setPhone("555-111-2222");

        Address addr2 = new Address();
        addr2.setLine1("456 Oak Avenue");
        addr2.setCity("Fairview");
        addr2.setState("CA");
        addr2.setPostalCode("94536");

        Provider provider2 = new Provider();
        provider2.setName("Wellness Dental Group");
        provider2.setSpecialty("Dentistry");
        provider2.setAddress(addr2);
        provider2.setPhone("555-222-3333");

        Address addr3 = new Address();
        addr3.setLine1("789 River Road");
        addr3.setCity("Riverside");
        addr3.setState("TX");
        addr3.setPostalCode("77320");

        Provider provider3 = new Provider();
        provider3.setName("Heart & Vascular Institute");
        provider3.setSpecialty("Cardiology");
        provider3.setAddress(addr3);
        provider3.setPhone("555-333-4444");

        Address addr4 = new Address();
        addr4.setLine1("321 Pine Street");
        addr4.setCity("Brookdale");
        addr4.setState("NY");
        addr4.setPostalCode("11201");

        Provider provider4 = new Provider();
        provider4.setName("Summit Orthopedic Center");
        provider4.setSpecialty("Orthopedics");
        provider4.setAddress(addr4);
        provider4.setPhone("555-444-5555");

// Save all providers
        providerRepository.saveAll(Arrays.asList(provider1, provider2, provider3, provider4));


// Create Claim 1
        Claim claim1 = new Claim();
        claim1.setClaimNumber("CLM-1001");
        claim1.setMember(member);
        claim1.setProvider(provider1);
        claim1.setServiceStartDate(LocalDate.of(2025, 1, 10));
        claim1.setServiceEndDate(LocalDate.of(2025, 1, 10));
        claim1.setReceivedDate(LocalDate.of(2025, 1, 15));
        claim1.setStatus(ClaimStatus.PAID);
        claim1.setTotalBilled(BigDecimal.valueOf(500.00));
        claim1.setTotalAllowed(BigDecimal.valueOf(450.00));
        claim1.setTotalPlanPaid(BigDecimal.valueOf(400.00));
        claim1.setTotalMemberResponsibility(BigDecimal.valueOf(50.00));

// Create Claim 2
        Claim claim2 = new Claim();
        claim2.setClaimNumber("CLM-1002");
        claim2.setMember(member);
        claim2.setProvider(provider2);
        claim2.setServiceStartDate(LocalDate.of(2025, 2, 5));
        claim2.setServiceEndDate(LocalDate.of(2025, 2, 6));
        claim2.setReceivedDate(LocalDate.of(2025, 2, 10));
        claim2.setStatus(ClaimStatus.IN_REVIEW);
        claim2.setTotalBilled(BigDecimal.valueOf(1200.00));
        claim2.setTotalAllowed(BigDecimal.valueOf(950.00));
        claim2.setTotalPlanPaid(BigDecimal.valueOf(700.00));
        claim2.setTotalMemberResponsibility(BigDecimal.valueOf(250.00));

// Create Claim 3
        Claim claim3 = new Claim();
        claim3.setClaimNumber("CLM-1003");
        claim3.setMember(member);
        claim3.setProvider(provider3);
        claim3.setServiceStartDate(LocalDate.of(2025, 3, 12));
        claim3.setServiceEndDate(LocalDate.of(2025, 3, 12));
        claim3.setReceivedDate(LocalDate.of(2025, 3, 17));
        claim3.setStatus(ClaimStatus.PAID);
        claim3.setTotalBilled(BigDecimal.valueOf(300.00));
        claim3.setTotalAllowed(BigDecimal.valueOf(275.00));
        claim3.setTotalPlanPaid(BigDecimal.valueOf(250.00));
        claim3.setTotalMemberResponsibility(BigDecimal.valueOf(25.00));

// Create Claim 4
        Claim claim4 = new Claim();
        claim4.setClaimNumber("CLM-1004");
        claim4.setMember(member);
        claim4.setProvider(provider4);
        claim4.setServiceStartDate(LocalDate.of(2025, 4, 20));
        claim4.setServiceEndDate(LocalDate.of(2025, 4, 21));
        claim4.setReceivedDate(LocalDate.of(2025, 4, 25));
        claim4.setStatus(ClaimStatus.DENIED);
        claim4.setTotalBilled(BigDecimal.valueOf(800.00));
        claim4.setTotalAllowed(BigDecimal.valueOf(0.00));
        claim4.setTotalPlanPaid(BigDecimal.valueOf(0.00));
        claim4.setTotalMemberResponsibility(BigDecimal.valueOf(800.00));

// Create Claim 5
        Claim claim5 = new Claim();
        claim5.setClaimNumber("CLM-1005");
        claim5.setMember(member);
        claim5.setProvider(provider1);
        claim5.setServiceStartDate(LocalDate.of(2025, 5, 10));
        claim5.setServiceEndDate(LocalDate.of(2025, 5, 10));
        claim5.setReceivedDate(LocalDate.of(2025, 5, 15));
        claim5.setStatus(ClaimStatus.PAID);
        claim5.setTotalBilled(BigDecimal.valueOf(250.00));
        claim5.setTotalAllowed(BigDecimal.valueOf(200.00));
        claim5.setTotalPlanPaid(BigDecimal.valueOf(180.00));
        claim5.setTotalMemberResponsibility(BigDecimal.valueOf(20.00));

// Create Claim 6
        Claim claim6 = new Claim();
        claim6.setClaimNumber("CLM-1006");
        claim6.setMember(member);
        claim6.setProvider(provider3);
        claim6.setServiceStartDate(LocalDate.of(2025, 6, 3));
        claim6.setServiceEndDate(LocalDate.of(2025, 6, 3));
        claim6.setReceivedDate(LocalDate.of(2025, 6, 8));
        claim6.setStatus(ClaimStatus.IN_REVIEW);
        claim6.setTotalBilled(BigDecimal.valueOf(600.00));
        claim6.setTotalAllowed(BigDecimal.valueOf(550.00));
        claim6.setTotalPlanPaid(BigDecimal.valueOf(0.00));
        claim6.setTotalMemberResponsibility(BigDecimal.valueOf(550.00));

// Create Claim 7
        Claim claim7 = new Claim();
        claim7.setClaimNumber("CLM-1007");
        claim7.setMember(member);
        claim7.setProvider(provider2);
        claim7.setServiceStartDate(LocalDate.of(2025, 7, 15));
        claim7.setServiceEndDate(LocalDate.of(2025, 7, 15));
        claim7.setReceivedDate(LocalDate.of(2025, 7, 20));
        claim7.setStatus(ClaimStatus.PAID);
        claim7.setTotalBilled(BigDecimal.valueOf(900.00));
        claim7.setTotalAllowed(BigDecimal.valueOf(850.00));
        claim7.setTotalPlanPaid(BigDecimal.valueOf(800.00));
        claim7.setTotalMemberResponsibility(BigDecimal.valueOf(50.00));

// Create Claim 8
        Claim claim8 = new Claim();
        claim8.setClaimNumber("CLM-1008");
        claim8.setMember(member);
        claim8.setProvider(provider4);
        claim8.setServiceStartDate(LocalDate.of(2025, 8, 1));
        claim8.setServiceEndDate(LocalDate.of(2025, 8, 1));
        claim8.setReceivedDate(LocalDate.of(2025, 8, 6));
        claim8.setStatus(ClaimStatus.IN_REVIEW);
        claim8.setTotalBilled(BigDecimal.valueOf(1500.00));
        claim8.setTotalAllowed(BigDecimal.valueOf(1200.00));
        claim8.setTotalPlanPaid(BigDecimal.valueOf(950.00));
        claim8.setTotalMemberResponsibility(BigDecimal.valueOf(250.00));

// Save all claims
        claimRepository.saveAll(Arrays.asList(
                claim1, claim2, claim3, claim4, claim5, claim6, claim7, claim8
        ));

    }
}
