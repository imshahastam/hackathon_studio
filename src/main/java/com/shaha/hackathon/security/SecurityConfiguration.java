package com.shaha.hackathon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) //csrf
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/hackathons/all").permitAll()
                        .requestMatchers("/users/registration").permitAll()
                        //.requestMatchers("/hackathons/create").hasRole("ORGANIZER")
                        .anyRequest().denyAll()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Используем JWT, без сессий
                .httpBasic(Customizer.withDefaults()); // Временно HTTP Basic (можно заменить на JWT)
        ;

        return http.build();
    }
}
