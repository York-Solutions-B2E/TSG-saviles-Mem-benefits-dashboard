package org.mbd.bememberbenefitsdashboard.service;

import org.mbd.bememberbenefitsdashboard.dto.AccumulatorDTO;
import org.mbd.bememberbenefitsdashboard.dto.ClaimDTO;
import org.mbd.bememberbenefitsdashboard.dto.EnrollmentDTO;
import org.mbd.bememberbenefitsdashboard.entity.*;
import org.mbd.bememberbenefitsdashboard.repository.ClaimRepository;
import org.mbd.bememberbenefitsdashboard.repository.EnrollmentRepository;
import org.mbd.bememberbenefitsdashboard.repository.MemberRepository;
import org.mbd.bememberbenefitsdashboard.repository.UserRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardService {
    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;
    private final ClaimRepository claimRepository;
    private final UserRepository userRepository;

    public DashboardService(EnrollmentRepository enrollmentRepository, MemberRepository memberRepository, ClaimRepository claimRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
        this.claimRepository = claimRepository;
        this.userRepository = userRepository;
    }

    public EnrollmentDTO getCurrentMemberEnrollment(Jwt jwt) {
        String sub = jwt.getClaim("sub");
        User user = userRepository.getByAuthSub(sub);
        Member member = user.getMember();

        return enrollmentRepository.findByMemberAndActiveTrue(member)
                .map(enrollment -> new EnrollmentDTO( //this map is a method on the Optional class. "if enrollment exists, then create EnrollmentDTO"
                        enrollment.getPlan(),
                        enrollment.getCoverageStart(),
                        enrollment.getCoverageEnd()
                ))
                .orElseThrow(() -> new RuntimeException("No active enrollment found for member"));
    }

    public List<AccumulatorDTO> getMemberAccumulator(Jwt jwt) {
        String sub = jwt.getClaim("sub");
        User user = userRepository.getByAuthSub(sub);
        Member member = user.getMember();
        Enrollment enrollment = enrollmentRepository.findByMemberAndActiveTrue(member)
                .orElseThrow(() -> new RuntimeException("No active enrollment found for member"));

        List<Accumulator> accumulators = enrollment.getAccumulators();

        List<AccumulatorDTO> accumulatorDTOs = new ArrayList<>();
        for (Accumulator acc : accumulators) {
            AccumulatorDTO dto = new AccumulatorDTO();
            dto.setAccumulatorType(acc.getType());
            dto.setUsedAmount(acc.getUsedAmount());
            dto.setLimitAmount(acc.getLimitAmount());
            accumulatorDTOs.add(dto);
        }
        return accumulatorDTOs;
    }

    public List<ClaimDTO> getMemberClaims(Jwt jwt) {
        String email = jwt.getClaim("email");
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        List<Claim> claims = claimRepository.findTop5ByMemberOrderByReceivedDateDesc(member);
        List<ClaimDTO> claimDTOs = new ArrayList<>();
        for (Claim claim : claims) {
            ClaimDTO dto = new ClaimDTO();
            dto.setId(claim.getId());
            dto.setClaimNumber(claim.getClaimNumber());
            dto.setClaimNumber(claim.getClaimNumber());
            dto.setStatus(claim.getStatus());
            dto.setTotalMemberResponsibility(claim.getTotalMemberResponsibility());
            claimDTOs.add(dto);
        }

        return claimDTOs;
    }

}
