package com.travelapp.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/h2-console/**").permitAll()  // Allow access to H2 Console
                .anyRequest().authenticated()  // Require authentication for other requests
            )
            .csrf(csrf -> csrf.disable())  // Disable CSRF for H2 Console
            .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()))  // Fix deprecated frameOptions issue
            .formLogin(form -> form
                .loginPage("/login") // Define custom login page (optional)
                .permitAll()  // Allow everyone to access login
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // Define custom logout URL
                .permitAll() // Allow logout for all users
            );

        return http.build();
    }
}
