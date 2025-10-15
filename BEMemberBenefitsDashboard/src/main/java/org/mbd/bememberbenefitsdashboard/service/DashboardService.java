package org.mbd.bememberbenefitsdashboard.service;

import org.mbd.bememberbenefitsdashboard.dto.EnrollmentDTO;
import org.mbd.bememberbenefitsdashboard.entity.Enrollment;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.mbd.bememberbenefitsdashboard.repository.EnrollmentRepository;
import org.mbd.bememberbenefitsdashboard.repository.MemberRepository;
import org.mbd.bememberbenefitsdashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

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

}
