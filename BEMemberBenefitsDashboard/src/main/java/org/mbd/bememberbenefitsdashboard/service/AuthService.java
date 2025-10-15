package org.mbd.bememberbenefitsdashboard.service;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.mbd.bememberbenefitsdashboard.entity.User;
import org.mbd.bememberbenefitsdashboard.repository.UserRepository;
import org.mbd.bememberbenefitsdashboard.security.JwtUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;



@Service
public class AuthService {
    private final UserRepository userRepository;
    private final GoogleIdTokenVerifier verifier;

    // Replace JacksonFactory with GsonFactory
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList("883984443893-2dmlii1eqk9daum3ihnm75ij9kqs80fd.apps.googleusercontent.com"))
                .build();
    }

    public String handleLogin(String idTokenString) {
        try {
            // Verify the token sent from the frontend
            GoogleIdToken idToken = verifier.verify(idTokenString);
            if (idToken == null) {
                throw new RuntimeException("Invalid ID token");
            }

            GoogleIdToken.Payload payload = idToken.getPayload();
            System.out.println(payload.getSubject());
            System.out.println(payload.getEmail());
            System.out.println(payload.get("given_name"));
            // Extract info from the token payload
            String sub = payload.getSubject(); // unique Google user ID
            String email = payload.getEmail();
            String firstName = (String) payload.get("given_name");
            String lastName = (String) payload.get("family_name");

            // Either find the user or create a new one
            User user = userRepository.findByAuthSub(sub)
                    .orElseGet(() -> userRepository.save(
                            new User("google", sub, email)
                    ));
            if (user.getMember() == null) {
                Member member = new Member();
                member.setUser(user);
                member.setFirstName(firstName);
                member.setLastName(lastName);
                member.setEmail(email);
                user.setMember(member);
                userRepository.save(user); // cascades member
            }


            // Generate your own JWT for your app (so frontend can use it for auth)
            return JwtUtils.generateToken(user);

        } catch (GeneralSecurityException | IOException e) {
            throw new RuntimeException("Token verification failed", e);
        }
    }
}

