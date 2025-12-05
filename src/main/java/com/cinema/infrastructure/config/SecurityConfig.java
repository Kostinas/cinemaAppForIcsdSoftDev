package com.cinema.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Δεν θέλουμε CSRF για H2 και για τα API μας προς το παρόν
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/api/**")
                )

                // Τι επιτρέπουμε χωρίς login
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/",                 // root
                                "/index.html",       // το frontend
                                "/static/**",
                                "/public/**",
                                "/css/**",
                                "/js/**",

                                // swagger
                                "/swagger-ui/**",
                                "/v3/api-docs/**",

                                // H2 console
                                "/h2-console/**",

                                // 🔑 AUTH (login δημόσιο)
                                "/api/auth/login",

                                // 🧍 REGISTER USER (δημόσιο)
                                "/api/users"          // POST /api/users
                        ).permitAll()
                        .anyRequest().authenticated()
                )

                // H2 console χρησιμοποιεί frame, οπότε επιτρέπουμε από same-origin
                .headers(headers -> headers
                        .frameOptions(frame -> frame.sameOrigin())
                )

                // Προς το παρόν δεν θέλουμε default login form του Spring
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        return http.build();
    }
}
