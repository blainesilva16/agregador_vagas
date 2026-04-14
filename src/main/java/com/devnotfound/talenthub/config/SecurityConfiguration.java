package com.devnotfound.talenthub.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;

//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .cors(Customizer.withDefaults())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
            		.requestMatchers("/auth/gerarToken",
                            "/swagger-ui/**",
                            "/v2/api-docs/**",
                            "/v3/api-docs/**",
                            "/swagger-resources/**",
                            "/webjars/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/customer").permitAll()
                    .requestMatchers(HttpMethod.PATCH, "/api/customer/reset-password").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/user").permitAll()
                    .requestMatchers(HttpMethod.PATCH, "/api/user/reset-password").permitAll()
                    .requestMatchers("/api/favorite", "/api/favorite/**").hasRole("CUSTOMER")
                    .requestMatchers("/api/customer", "/api/customer/**").hasRole("CUSTOMER")
                    .requestMatchers("/api/user", "/api/user/**").hasRole("USER")
                    .requestMatchers(HttpMethod.GET, "/jobs/public").permitAll()
                    .requestMatchers(HttpMethod.GET, "/jobs/states").permitAll()
                    .requestMatchers(HttpMethod.GET, "/jobs/cities").permitAll()
                    
                    
                    .anyRequest().authenticated()
            )
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}