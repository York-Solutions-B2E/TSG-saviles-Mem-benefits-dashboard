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

    public void findOrCreateMember(Jwt jwt) {
        String sub = jwt.getSubject();
        String email = jwt.getClaim("email");
        String firstName = jwt.getClaim("given_name");
        String lastName = jwt.getClaim("family_name");
        User user = userRepository.findByAuthSub(sub)
                .orElseGet(() -> { //If no user found, create new user
                    User newUser = new User(); //Also creating anon function that creates/saves new user w/ lambda
                    newUser.setAuthSub(sub);
                    newUser.setAuthProvider("google");
                    newUser.setEmail(email);

                    return userRepository.save(newUser);
                });

        // Create member if they don't exist
        if (user.getMember() == null) {
            Member member = new Member();
            member.setUser(user);
            member.setEmail(email);
            member.setFirstName(firstName);
            member.setLastName(lastName);

            user.setMember(member);
            //populate dummy data
            dummyDataService.populateDummyData(member);

            userRepository.save(user);
        }

    }

}

