package org.mbd.bememberbenefitsdashboard.controller;
import org.mbd.bememberbenefitsdashboard.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/auth/me")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public ResponseEntity<Void> validateLogin(@AuthenticationPrincipal Jwt jwt) { //@AuthPrinc is a way for us to decode jwt to grab info
        authService.findOrCreateMember(jwt);                      //Don't really need ResponseEntity, but it's an explicit
        return ResponseEntity.ok().build();                      // way to say "we're expecting to be return a 200 status code".
    }


}

