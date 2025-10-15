package org.mbd.bememberbenefitsdashboard.controller;
import lombok.*;
import org.mbd.bememberbenefitsdashboard.dto.LoginRequest;
import org.mbd.bememberbenefitsdashboard.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<?> loginWithGoogle(@RequestBody LoginRequest request) {
        // The request contains the Google ID token
        return ResponseEntity.ok(authService.handleLogin(request.getIdToken()));
    }


}

