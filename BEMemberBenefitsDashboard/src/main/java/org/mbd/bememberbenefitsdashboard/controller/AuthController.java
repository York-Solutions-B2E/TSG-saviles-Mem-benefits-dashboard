package org.mbd.bememberbenefitsdashboard.controller;
import lombok.*;
import org.mbd.bememberbenefitsdashboard.dto.LoginRequest;
import org.mbd.bememberbenefitsdashboard.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Getter
@Setter
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth/me")
public class AuthController {
    private AuthService authService;

    @PostMapping
    public ResponseEntity<?> loginWithGoogle(@RequestBody LoginRequest request) {
        // The request contains the Google ID token
        return ResponseEntity.ok(authService.handleLogin(request.getIdToken()));
    }


}

