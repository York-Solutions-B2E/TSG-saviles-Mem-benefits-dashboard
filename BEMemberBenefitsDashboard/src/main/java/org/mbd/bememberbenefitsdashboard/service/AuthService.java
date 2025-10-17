package org.mbd.bememberbenefitsdashboard.service;
import org.mbd.bememberbenefitsdashboard.entity.Member;
import org.mbd.bememberbenefitsdashboard.entity.User;
import org.mbd.bememberbenefitsdashboard.repository.UserRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;



@Service
public class AuthService {
    private final UserRepository userRepository;
    private final DummyDataService dummyDataService;

    public AuthService(UserRepository userRepository, DummyDataService dummyDataService) {
        this.userRepository = userRepository;
        this.dummyDataService = dummyDataService;
    }


    // Accept Jwt as single parameter
    public void findOrCreateMember(Jwt jwt) {
        String sub = jwt.getSubject();
        String email = jwt.getClaim("email");
        String firstName = jwt.getClaim("given_name");
        String lastName = jwt.getClaim("family_name");

        // Find existing user or create a new one
        User user = userRepository.findByAuthSub(sub)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setAuthSub(sub);
                    newUser.setAuthProvider("google");
                    newUser.setEmail(email);

                    return userRepository.save(newUser);
                });

        // Create member if it doesn't exist
        if (user.getMember() == null) {
            Member member = new Member();
            member.setUser(user);
            member.setEmail(email);
            member.setFirstName(firstName);
            member.setLastName(lastName);

            // Link member to user
            user.setMember(member);
            //populate dummy data
            dummyDataService.populateDummyData(member);

            userRepository.save(user);
        }

    }

}

