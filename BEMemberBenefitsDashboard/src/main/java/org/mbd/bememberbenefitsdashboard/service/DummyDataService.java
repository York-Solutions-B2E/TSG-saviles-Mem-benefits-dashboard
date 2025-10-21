package org.mbd.bememberbenefitsdashboard.service;

import lombok.RequiredArgsConstructor;
import org.mbd.bememberbenefitsdashboard.entity.*;
import org.mbd.bememberbenefitsdashboard.enums.AccumulatorType;
import org.mbd.bememberbenefitsdashboard.enums.ClaimStatus;
import org.mbd.bememberbenefitsdashboard.enums.NetworkTier;
import org.mbd.bememberbenefitsdashboard.enums.PlanType;
import org.mbd.bememberbenefitsdashboard.repository.*;
import org.mbd.bememberbenefitsdashboard.repository.ClaimStatusEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DummyDataService {
    private final PlanRepository planRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final AccumulatorRepository accumulatorRepository;
    private final ProviderRepository providerRepository;
    private final ClaimRepository claimRepository;
    private final ClaimLineRepository claimLineRepository;
    private final ClaimStatusEventRepository claimStatusEventRepository;


    @Transactional
    public void populateDummyData(Member member) {
        // Check if member already has enrollments -> skip if yes
        boolean hasEnrollment = enrollmentRepository.findByMemberAndActiveTrue(member).isPresent();
        if (hasEnrollment) return;

        //  Create Plans
        Plan goldPlan = new Plan(null, "Gold PPO", PlanType.PPO, "Prime Network", 2025);
        Plan silverPlan = new Plan(null, "Silver HMO", PlanType.HMO, "CarePlus Network", 2025);
        Plan bronzePlan = new Plan(null, "Bronze EPO", PlanType.EPO, "HealthFirst Network", 2025);
        planRepository.save(goldPlan);
        planRepository.save(silverPlan);
        planRepository.save(bronzePlan);

        // Create Enrollment for the member
        Enrollment enrollment = new Enrollment();
        enrollment.setMember(member);
        enrollment.setPlan(goldPlan);
        enrollment.setCoverageStart(LocalDate.of(2025, 1, 1));
        enrollment.setCoverageEnd(LocalDate.of(2025, 12, 31));
        enrollment.setActive(true);

        enrollmentRepository.save(enrollment);

        // Create Accumulators linked to the enrollment
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

        // Create Providers and addresses
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

        providerRepository.saveAll(Arrays.asList(provider1, provider2, provider3, provider4));


        // Create Claims
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

        Claim claim9 = new Claim();
        claim9.setClaimNumber("CLM-1009");
        claim9.setMember(member);
        claim9.setProvider(provider2);
        claim9.setServiceStartDate(LocalDate.of(2025, 8, 10));
        claim9.setServiceEndDate(LocalDate.of(2025, 8, 10));
        claim9.setReceivedDate(LocalDate.of(2025, 8, 15));
        claim9.setStatus(ClaimStatus.PROCESSED);
        claim9.setTotalBilled(BigDecimal.valueOf(2200.00));
        claim9.setTotalAllowed(BigDecimal.valueOf(1800.00));
        claim9.setTotalPlanPaid(BigDecimal.valueOf(1500.00));
        claim9.setTotalMemberResponsibility(BigDecimal.valueOf(300.00));

        Claim claim10 = new Claim();
        claim10.setClaimNumber("CLM-1010");
        claim10.setMember(member);
        claim10.setProvider(provider3);
        claim10.setServiceStartDate(LocalDate.of(2025, 7, 25));
        claim10.setServiceEndDate(LocalDate.of(2025, 7, 25));
        claim10.setReceivedDate(LocalDate.of(2025, 7, 29));
        claim10.setStatus(ClaimStatus.DENIED);
        claim10.setTotalBilled(BigDecimal.valueOf(780.00));
        claim10.setTotalAllowed(BigDecimal.valueOf(600.00));
        claim10.setTotalPlanPaid(BigDecimal.valueOf(0.00));
        claim10.setTotalMemberResponsibility(BigDecimal.valueOf(600.00));

        Claim claim11 = new Claim();
        claim11.setClaimNumber("CLM-1011");
        claim11.setMember(member);
        claim11.setProvider(provider1);
        claim11.setServiceStartDate(LocalDate.of(2025, 9, 5));
        claim11.setServiceEndDate(LocalDate.of(2025, 9, 5));
        claim11.setReceivedDate(LocalDate.of(2025, 9, 9));
        claim11.setStatus(ClaimStatus.SUBMITTED);
        claim11.setTotalBilled(BigDecimal.valueOf(340.00));
        claim11.setTotalAllowed(BigDecimal.valueOf(300.00));
        claim11.setTotalPlanPaid(BigDecimal.valueOf(250.00));
        claim11.setTotalMemberResponsibility(BigDecimal.valueOf(50.00));

        Claim claim12 = new Claim();
        claim12.setClaimNumber("CLM-1012");
        claim12.setMember(member);
        claim12.setProvider(provider4);
        claim12.setServiceStartDate(LocalDate.of(2025, 6, 12));
        claim12.setServiceEndDate(LocalDate.of(2025, 6, 12));
        claim12.setReceivedDate(LocalDate.of(2025, 6, 18));
        claim12.setStatus(ClaimStatus.IN_REVIEW);
        claim12.setTotalBilled(BigDecimal.valueOf(5000.00));
        claim12.setTotalAllowed(BigDecimal.valueOf(4200.00));
        claim12.setTotalPlanPaid(BigDecimal.valueOf(3700.00));
        claim12.setTotalMemberResponsibility(BigDecimal.valueOf(500.00));

        Claim claim13 = new Claim();
        claim13.setClaimNumber("CLM-1013");
        claim13.setMember(member);
        claim13.setProvider(provider2);
        claim13.setServiceStartDate(LocalDate.of(2025, 10, 3));
        claim13.setServiceEndDate(LocalDate.of(2025, 10, 3));
        claim13.setReceivedDate(LocalDate.of(2025, 10, 8));
        claim13.setStatus(ClaimStatus.IN_REVIEW);
        claim13.setTotalBilled(BigDecimal.valueOf(1300.00));
        claim13.setTotalAllowed(BigDecimal.valueOf(1000.00));
        claim13.setTotalPlanPaid(BigDecimal.valueOf(700.00));
        claim13.setTotalMemberResponsibility(BigDecimal.valueOf(300.00));

        Claim claim14 = new Claim();
        claim14.setClaimNumber("CLM-1014");
        claim14.setMember(member);
        claim14.setProvider(provider1);
        claim14.setServiceStartDate(LocalDate.of(2025, 5, 20));
        claim14.setServiceEndDate(LocalDate.of(2025, 5, 20));
        claim14.setReceivedDate(LocalDate.of(2025, 5, 25));
        claim14.setStatus(ClaimStatus.PAID);
        claim14.setTotalBilled(BigDecimal.valueOf(950.00));
        claim14.setTotalAllowed(BigDecimal.valueOf(850.00));
        claim14.setTotalPlanPaid(BigDecimal.valueOf(800.00));
        claim14.setTotalMemberResponsibility(BigDecimal.valueOf(50.00));

        Claim claim15 = new Claim();
        claim15.setClaimNumber("CLM-1015");
        claim15.setMember(member);
        claim15.setProvider(provider3);
        claim15.setServiceStartDate(LocalDate.of(2025, 4, 4));
        claim15.setServiceEndDate(LocalDate.of(2025, 4, 4));
        claim15.setReceivedDate(LocalDate.of(2025, 4, 10));
        claim15.setStatus(ClaimStatus.DENIED);
        claim15.setTotalBilled(BigDecimal.valueOf(2100.00));
        claim15.setTotalAllowed(BigDecimal.valueOf(0.00));
        claim15.setTotalPlanPaid(BigDecimal.valueOf(0.00));
        claim15.setTotalMemberResponsibility(BigDecimal.valueOf(2100.00));

        Claim claim16 = new Claim();
        claim16.setClaimNumber("CLM-1016");
        claim16.setMember(member);
        claim16.setProvider(provider4);
        claim16.setServiceStartDate(LocalDate.of(2025, 9, 15));
        claim16.setServiceEndDate(LocalDate.of(2025, 9, 15));
        claim16.setReceivedDate(LocalDate.of(2025, 9, 20));
        claim16.setStatus(ClaimStatus.PAID);
        claim16.setTotalBilled(BigDecimal.valueOf(1800.00));
        claim16.setTotalAllowed(BigDecimal.valueOf(1500.00));
        claim16.setTotalPlanPaid(BigDecimal.valueOf(1300.00));
        claim16.setTotalMemberResponsibility(BigDecimal.valueOf(200.00));

        claimRepository.saveAll(Arrays.asList(
                claim1, claim2, claim3, claim4, claim5, claim6, claim7, claim8,
                claim9, claim10, claim11, claim12, claim13, claim14, claim15, claim16
        ));


        //  Create ClaimLines for each claim
        ClaimLine line1a = new ClaimLine(null, claim1, 1, "99213", "Office visit, established patient",
                BigDecimal.valueOf(200.00), BigDecimal.valueOf(180.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(150.00),
                BigDecimal.valueOf(30.00));

        ClaimLine line1b = new ClaimLine(null, claim1, 2, "87070", "Lab culture, bacterial",
                BigDecimal.valueOf(300.00), BigDecimal.valueOf(270.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(250.00),
                BigDecimal.valueOf(20.00));

        ClaimLine line2a = new ClaimLine(null, claim2, 1, "41899", "Dental surgery, unspecified procedure",
                BigDecimal.valueOf(600.00), BigDecimal.valueOf(500.00),
                BigDecimal.valueOf(50.00), BigDecimal.valueOf(20.00),
                BigDecimal.valueOf(30.00), BigDecimal.valueOf(400.00),
                BigDecimal.valueOf(100.00));

        ClaimLine line2b = new ClaimLine(null, claim2, 2, "70320", "Dental X-ray, complete series",
                BigDecimal.valueOf(200.00), BigDecimal.valueOf(180.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(150.00),
                BigDecimal.valueOf(30.00));

        ClaimLine line3a = new ClaimLine(null, claim3, 1, "93000", "Electrocardiogram complete",
                BigDecimal.valueOf(150.00), BigDecimal.valueOf(130.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(120.00),
                BigDecimal.valueOf(10.00));

        ClaimLine line4a = new ClaimLine(null, claim4, 1, "29888", "Knee arthroscopy, ACL reconstruction",
                BigDecimal.valueOf(800.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(800.00));

        ClaimLine line5a = new ClaimLine(null, claim5, 1, "99212", "Office visit, brief",
                BigDecimal.valueOf(250.00), BigDecimal.valueOf(200.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(180.00),
                BigDecimal.valueOf(20.00));

        ClaimLine line6a = new ClaimLine(null, claim6, 1, "93306", "Echocardiogram complete",
                BigDecimal.valueOf(600.00), BigDecimal.valueOf(550.00),
                BigDecimal.valueOf(50.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(550.00));

        ClaimLine line7a = new ClaimLine(null, claim7, 1, "99214", "Office visit, established, moderate",
                BigDecimal.valueOf(400.00), BigDecimal.valueOf(370.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(340.00),
                BigDecimal.valueOf(30.00));

        ClaimLine line7b = new ClaimLine(null, claim7, 2, "80050", "General health panel",
                BigDecimal.valueOf(500.00), BigDecimal.valueOf(480.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(460.00),
                BigDecimal.valueOf(20.00));

        ClaimLine line8a = new ClaimLine(null, claim8, 1, "73721", "MRI lower extremity joint",
                BigDecimal.valueOf(1500.00), BigDecimal.valueOf(1200.00),
                BigDecimal.valueOf(100.00), BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(100.00), BigDecimal.valueOf(950.00),
                BigDecimal.valueOf(250.00));

        ClaimLine line9a = new ClaimLine(null, claim9, 1, "99213", "Office visit, established patient",
                BigDecimal.valueOf(150.00), BigDecimal.valueOf(120.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(30.00));

        ClaimLine line10a = new ClaimLine(null, claim10, 1, "80053", "Comprehensive metabolic panel",
                BigDecimal.valueOf(250.00), BigDecimal.valueOf(200.00),
                BigDecimal.valueOf(30.00), BigDecimal.valueOf(20.00),
                BigDecimal.valueOf(30.00), BigDecimal.valueOf(150.00),
                BigDecimal.valueOf(50.00));

        ClaimLine line11a = new ClaimLine(null, claim11, 1, "J1100", "Injection, dexamethasone sodium phosphate",
                BigDecimal.valueOf(75.00), BigDecimal.valueOf(60.00),
                BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(10.00), BigDecimal.valueOf(45.00),
                BigDecimal.valueOf(15.00));

        ClaimLine line12a = new ClaimLine(null, claim12, 1, "81002", "Urinalysis, non-automated",
                BigDecimal.valueOf(40.00), BigDecimal.valueOf(35.00),
                BigDecimal.valueOf(5.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(5.00), BigDecimal.valueOf(30.00),
                BigDecimal.valueOf(10.00));

        ClaimLine line13a = new ClaimLine(null, claim13, 1, "97110", "Physical therapy, therapeutic exercises",
                BigDecimal.valueOf(200.00), BigDecimal.valueOf(160.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(130.00),
                BigDecimal.valueOf(30.00));

        ClaimLine line14a = new ClaimLine(null, claim14, 1, "70450", "CT head without contrast",
                BigDecimal.valueOf(900.00), BigDecimal.valueOf(750.00),
                BigDecimal.valueOf(80.00), BigDecimal.valueOf(40.00),
                BigDecimal.valueOf(80.00), BigDecimal.valueOf(600.00),
                BigDecimal.valueOf(150.00));

        ClaimLine line15a = new ClaimLine(null, claim15, 1, "93000", "Electrocardiogram, routine ECG with report",
                BigDecimal.valueOf(120.00), BigDecimal.valueOf(100.00),
                BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(10.00), BigDecimal.valueOf(85.00),
                BigDecimal.valueOf(15.00));

        ClaimLine line16a = new ClaimLine(null, claim16, 1, "90791", "Psychiatric diagnostic evaluation",
                BigDecimal.valueOf(250.00), BigDecimal.valueOf(200.00),
                BigDecimal.valueOf(25.00), BigDecimal.valueOf(15.00),
                BigDecimal.valueOf(25.00), BigDecimal.valueOf(160.00),
                BigDecimal.valueOf(40.00));

        ClaimLine line3b = new ClaimLine(null, claim3, 2, "97140", "Manual therapy techniques",
                BigDecimal.valueOf(180.00), BigDecimal.valueOf(150.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(120.00),
                BigDecimal.valueOf(30.00));

        ClaimLine line5b = new ClaimLine(null, claim5, 2, "85025", "Complete blood count (CBC)",
                BigDecimal.valueOf(90.00), BigDecimal.valueOf(70.00),
                BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(10.00), BigDecimal.valueOf(55.00),
                BigDecimal.valueOf(15.00));

        ClaimLine line7c = new ClaimLine(null, claim7, 3, "73630", "X-ray of foot, complete",
                BigDecimal.valueOf(175.00), BigDecimal.valueOf(150.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(10.00),
                BigDecimal.valueOf(20.00), BigDecimal.valueOf(120.00),
                BigDecimal.valueOf(30.00));

        ClaimLine line9b = new ClaimLine(null, claim9, 2, "J1885", "Injection, ketorolac tromethamine",
                BigDecimal.valueOf(100.00), BigDecimal.valueOf(80.00),
                BigDecimal.valueOf(10.00), BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(10.00), BigDecimal.valueOf(65.00),
                BigDecimal.valueOf(15.00));

        ClaimLine line10b = new ClaimLine(null, claim10, 2, "36415", "Collection of venous blood by venipuncture",
                BigDecimal.valueOf(25.00), BigDecimal.valueOf(20.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(0.00), BigDecimal.valueOf(20.00),
                BigDecimal.valueOf(5.00));

        ClaimLine line12b = new ClaimLine(null, claim12, 2, "87086", "Urine culture, bacterial",
                BigDecimal.valueOf(60.00), BigDecimal.valueOf(50.00),
                BigDecimal.valueOf(5.00), BigDecimal.valueOf(5.00),
                BigDecimal.valueOf(5.00), BigDecimal.valueOf(40.00),
                BigDecimal.valueOf(10.00));

        ClaimLine line14b = new ClaimLine(null, claim14, 2, "Q9967", "Contrast agent, iodixanol injection",
                BigDecimal.valueOf(300.00), BigDecimal.valueOf(250.00),
                BigDecimal.valueOf(25.00), BigDecimal.valueOf(15.00),
                BigDecimal.valueOf(25.00), BigDecimal.valueOf(185.00),
                BigDecimal.valueOf(65.00));


        claimLineRepository.saveAll(Arrays.asList(
                line1a, line1b,
                line2a, line2b,
                line3a, line3b,
                line4a,
                line5a, line5b,
                line6a,
                line7a, line7b, line7c,
                line8a,
                line9a, line9b,
                line10a, line10b,
                line11a,
                line12a, line12b,
                line13a,
                line14a, line14b,
                line15a,
                line16a
        ));

        OffsetDateTime now = OffsetDateTime.now();

        for (Claim claim : claimRepository.findAll()) {
            // Creates submitted event for all claims
            ClaimStatusEvent submittedEvent = new ClaimStatusEvent();
            submittedEvent.setClaim(claim);
            submittedEvent.setStatus(ClaimStatus.SUBMITTED);
            submittedEvent.setOccurredAt(now.minusDays(10));
            submittedEvent.setNote("Claim submitted");
            claimStatusEventRepository.save(submittedEvent);

            switch (claim.getStatus()) { //Look at statuses and builds events to be "sequential"
                case IN_REVIEW -> {
                    ClaimStatusEvent inReviewEvent = new ClaimStatusEvent();
                    inReviewEvent.setClaim(claim);
                    inReviewEvent.setStatus(ClaimStatus.IN_REVIEW);
                    inReviewEvent.setOccurredAt(now.minusDays(5));
                    inReviewEvent.setNote("Claim under review");
                    claimStatusEventRepository.save(inReviewEvent);
                }

                case PROCESSED -> {
                    ClaimStatusEvent inReviewEvent = new ClaimStatusEvent();
                    inReviewEvent.setClaim(claim);
                    inReviewEvent.setStatus(ClaimStatus.IN_REVIEW);
                    inReviewEvent.setOccurredAt(now.minusDays(5));
                    inReviewEvent.setNote("Claim under review");
                    claimStatusEventRepository.save(inReviewEvent);

                    ClaimStatusEvent processedEvent = new ClaimStatusEvent();
                    processedEvent.setClaim(claim);
                    processedEvent.setStatus(ClaimStatus.PROCESSED);
                    processedEvent.setOccurredAt(now.minusDays(2));
                    processedEvent.setNote("Claim processed");
                    claimStatusEventRepository.save(processedEvent);
                }

                case PAID -> {
                    ClaimStatusEvent inReviewEvent = new ClaimStatusEvent();
                    inReviewEvent.setClaim(claim);
                    inReviewEvent.setStatus(ClaimStatus.IN_REVIEW);
                    inReviewEvent.setOccurredAt(now.minusDays(5));
                    inReviewEvent.setNote("Claim under review");
                    claimStatusEventRepository.save(inReviewEvent);

                    ClaimStatusEvent processedEvent = new ClaimStatusEvent();
                    processedEvent.setClaim(claim);
                    processedEvent.setStatus(ClaimStatus.PROCESSED);
                    processedEvent.setOccurredAt(now.minusDays(2));
                    processedEvent.setNote("Claim processed");
                    claimStatusEventRepository.save(processedEvent);

                    ClaimStatusEvent paidEvent = new ClaimStatusEvent();
                    paidEvent.setClaim(claim);
                    paidEvent.setStatus(ClaimStatus.PAID);
                    paidEvent.setOccurredAt(now);
                    paidEvent.setNote("Claim paid");
                    claimStatusEventRepository.save(paidEvent);
                }

                case DENIED -> {
                    ClaimStatusEvent inReviewEvent = new ClaimStatusEvent();
                    inReviewEvent.setClaim(claim);
                    inReviewEvent.setStatus(ClaimStatus.IN_REVIEW);
                    inReviewEvent.setOccurredAt(now.minusDays(5));
                    inReviewEvent.setNote("Claim under review");
                    claimStatusEventRepository.save(inReviewEvent);

                    ClaimStatusEvent deniedEvent = new ClaimStatusEvent();
                    deniedEvent.setClaim(claim);
                    deniedEvent.setStatus(ClaimStatus.DENIED);
                    deniedEvent.setOccurredAt(now.minusDays(1));
                    deniedEvent.setNote("Claim denied");
                    claimStatusEventRepository.save(deniedEvent);
                }
            }
        }

    }
}
