package org.mbd.bememberbenefitsdashboard.service;

import org.mbd.bememberbenefitsdashboard.entity.Enrollment;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.mbd.bememberbenefitsdashboard.repository.EnrollmentRepository;
import org.mbd.bememberbenefitsdashboard.repository.MemberRepository;
import org.mbd.bememberbenefitsdashboard.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DashboardService {
    private final EnrollmentRepository enrollmentRepository;
    private final MemberRepository memberRepository;
    private final UserRepository userRepository;

    public DashboardService(EnrollmentRepository enrollmentRepository, MemberRepository memberRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.memberRepository = memberRepository;
        this.userRepository = userRepository;
    }

    public Enrollment getCurrentMemberEnrollment() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found for email: " + email));

        return enrollmentRepository.findByMemberAndActiveTrue(member)
                .orElseThrow(() -> new RuntimeException("No active enrollment found for member: " + email));
    }

}
