package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.http.HttpMethod;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(HttpMethod.POST, "/api/expenses")
                        .hasRole("EMPLOYEE")

                        .requestMatchers("/api/expenses/*/approve")
                        .hasRole("MANAGER")

                        .requestMatchers("/api/expenses/*/reject")
                        .hasRole("MANAGER")

                        .requestMatchers("/api/expenses/*/reimburse")
                        .hasRole("FINANCE")

                        .anyRequest()
                        .authenticated()
                )
                .httpBasic(httpBasic -> {});

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager users(PasswordEncoder passwordEncoder) {

        UserDetails employee = User.builder()
                .username("employee")
                .password(passwordEncoder.encode("employee123"))
                .roles("EMPLOYEE")
                .build();

        UserDetails manager = User.builder()
                .username("manager")
                .password(passwordEncoder.encode("manager123"))
                .roles("MANAGER")
                .build();

        UserDetails finance = User.builder()
                .username("finance")
                .password(passwordEncoder.encode("finance123"))
                .roles("FINANCE")
                .build();

        return new InMemoryUserDetailsManager(
                employee,
                manager,
                finance
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}