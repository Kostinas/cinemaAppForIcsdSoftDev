package com.cinema.infrastructure.config;

import com.cinema.infrastructure.security.AuthFilter;
import com.cinema.infrastructure.security.AuditLogger;
import com.cinema.infrastructure.security.RateLimitFilter;
import com.cinema.infrastructure.security.TokenService;
import com.cinema.infrastructure.security.TokenValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


public class SecurityConfig {

    @Bean
    public TokenService tokenService(
            @Value("${security.jwt.secret:change-me}") String secret,
            @Value("${security.jwt.expiration-seconds:3600}") long expirationSeconds
    ) {
        return new TokenService(secret, expirationSeconds);
    }


    @Bean
    public TokenValidator tokenValidator(
            @Value("${security.jwt.secret:change-me}") String secret
    ) {
        return new TokenValidator(secret);
    }

    @Bean
    public AuthFilter authFilter(TokenValidator tokenValidator) {
        return new AuthFilter(tokenValidator);
    }


    @Bean
    public RateLimitFilter rateLimitFilter() {
        return new RateLimitFilter(100, 60);
    }

    @Bean
    public AuditLogger auditLogger() {
        return new AuditLogger();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthFilter authFilter,
            RateLimitFilter rateLimitFilter
    ) throws Exception {

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // endpoints που είναι public
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // όλα τα υπόλοιπα θέλουν auth
                        .anyRequest().authenticated()
                )
                // πρώτα rate limit για να κόβει brute force
                .addFilterBefore(rateLimitFilter, UsernamePasswordAuthenticationFilter.class)
                // μετά JWT auth filter
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
