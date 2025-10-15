package org.mbd.bememberbenefitsdashboard.service;

import org.mbd.bememberbenefitsdashboard.dto.AccumulatorDTO;
import org.mbd.bememberbenefitsdashboard.dto.EnrollmentDTO;
import org.mbd.bememberbenefitsdashboard.entity.Accumulator;
import org.mbd.bememberbenefitsdashboard.entity.Enrollment;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.mbd.bememberbenefitsdashboard.repository.EnrollmentRepository;
import org.mbd.bememberbenefitsdashboard.repository.MemberRepository;
import org.mbd.bememberbenefitsdashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DashboardService {
    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;

    public DashboardService(EnrollmentRepository enrollmentRepository, MemberRepository memberRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
    }

    public EnrollmentDTO getCurrentMemberEnrollment(Jwt jwt) {
        String email = jwt.getClaim("email");
        return memberRepository.findByEmail(email)
                .flatMap(member -> enrollmentRepository.findByMemberAndActiveTrue(member))
                .map(enrollment -> new EnrollmentDTO(
                        enrollment.getPlan(),
                        enrollment.getCoverageStart(),
                        enrollment.getCoverageEnd()
                ))
                .orElseGet(() -> new EnrollmentDTO(null, null, null)); // empty DTO if none
    }

    public List<AccumulatorDTO> getMemberAccumulator(Jwt jwt) {
        String email = jwt.getClaim("email");
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        Enrollment enrollment = enrollmentRepository.findByMemberAndActiveTrue(member)
                .orElseThrow(() -> new RuntimeException("No active enrollment found for member"));

        List<Accumulator> accumulators = enrollment.getAccumulators();

        List<AccumulatorDTO> accumulatorDTOs = new ArrayList<>();
        for (Accumulator acc : accumulators) {
            AccumulatorDTO dto = new AccumulatorDTO();
            dto.setAccumulatorType(acc.getType());
            dto.setUsedAmount(acc.getUsedAmount());
            dto.setLimitAmount(acc.getLimitAmount());
        }
        return accumulatorDTOs;
    }

}
