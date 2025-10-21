package org.mbd.bememberbenefitsdashboard.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration //Let's spring know to load this
@EnableWebSecurity // allows us to define that this is for Auth
public class SecurityConfig { // This class basically defines the security behavior for all incoming HTTP requests

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(Customizer.withDefaults()) //Use default core settings
                .csrf(csrf -> csrf.disable()) //don't need CSRF with our setup since we're using JWT
                .authorizeHttpRequests(auth -> auth // for all HTTP requests
                        .requestMatchers("/api/auth/**").permitAll() // permit this end point
                        .anyRequest().authenticated() // all others require auth
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> {})); //tells spring backend will accept JWT token. Validate it automatically

        return http.build(); //compiles all these configs which spring will use for every request
    }

}

