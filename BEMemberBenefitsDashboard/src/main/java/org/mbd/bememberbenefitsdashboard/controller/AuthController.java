package org.mbd.bememberbenefitsdashboard.controller;
import lombok.*;
import org.mbd.bememberbenefitsdashboard.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@Getter
@Setter
@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth/me")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<Void> validateLogin(@AuthenticationPrincipal Jwt jwt) {
        authService.findOrCreateMember(jwt);
        return ResponseEntity.ok().build();
    }


}

