package org.mbd.bememberbenefitsdashboard.controller;
import org.mbd.bememberbenefitsdashboard.dto.AccumulatorDTO;
import org.mbd.bememberbenefitsdashboard.dto.ClaimDTO;
import org.mbd.bememberbenefitsdashboard.dto.EnrollmentDTO;
import org.mbd.bememberbenefitsdashboard.service.DashboardService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;


@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/dashboard")
public class DashboardController {
    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/enrollment")
    public EnrollmentDTO getMemberEnrollment(@AuthenticationPrincipal Jwt jwt) {
        return dashboardService.getCurrentMemberEnrollment(jwt);
    }

    @GetMapping("/accumulator")
    public List<AccumulatorDTO> getMemberAccumulator(@AuthenticationPrincipal Jwt jwt) {
        return dashboardService.getMemberAccumulator(jwt);
    }

    @GetMapping("/claims")
    public List<ClaimDTO> getMemberClaims(@AuthenticationPrincipal Jwt jwt) {
        return dashboardService.getMemberClaims(jwt);
    }

}
