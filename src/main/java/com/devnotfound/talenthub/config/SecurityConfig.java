package com.devnotfound.talenthub.config;

import com.devnotfound.talenthub.service.CustomCustomerDetailsService;
import com.devnotfound.talenthub.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Configuração para área ADMIN
    @Bean
    @Order(1)
    public SecurityFilterChain userSecurity(HttpSecurity http,
                                             CustomUserDetailsService userDetailsService) throws Exception {
        http.securityMatcher("/user/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/user/login-user").permitAll()
                        .anyRequest().hasRole("USER"))
                .formLogin(login -> login
                        .loginPage("/user/login-user")
                        .defaultSuccessUrl("/user/home", true)
                        .permitAll())
                .userDetailsService(userDetailsService);

        return http.build();
    }

    // Configuração para área CUSTOMER
    @Bean
    @Order(2)
    public SecurityFilterChain customerSecurity(HttpSecurity http,
                                                CustomCustomerDetailsService customerDetailsService) throws Exception {
        http.securityMatcher("/customer/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/customer/login-customer").permitAll()
                        .anyRequest().hasRole("CUSTOMER"))
                .formLogin(login -> login
                        .loginPage("/customer/login-customer")
                        .defaultSuccessUrl("/customer/home", true)
                        .permitAll())
                .userDetailsService(customerDetailsService);

        return http.build();
    }

    // Configuração para API REST (sem autenticação)
    @Bean
    @Order(3)
    public SecurityFilterChain apiSecurity(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }
}