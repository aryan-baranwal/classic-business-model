package com.classicmodels.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class VaultSecurityConfig {

    // ================= USERS =================
    @Value("${users.user1.username:}") private String u1;
    @Value("${users.user1.password:}") private String p1;

    @Value("${users.user2.username:}") private String u2;
    @Value("${users.user2.password:}") private String p2;

    @Value("${users.user3.username:}") private String u3;
    @Value("${users.user3.password:}") private String p3;

    @Value("${users.user4.username:}") private String u4;
    @Value("${users.user4.password:}") private String p4;

    @Value("${users.user5.username:}") private String u5;
    @Value("${users.user5.password:}") private String p5;

    // DEBUG (REMOVE IN PRODUCTION)
    @PostConstruct
    public void debug() {
        System.out.println("USER1 = " + u1);
        System.out.println("PASS1 = " + p1);
    }

    // ================= USERS SETUP =================
    @Bean
    public InMemoryUserDetailsManager userDetailsManager() {

        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        // Aryan — OFFICE + REPORT (was: OFFICE only)
        if (u1 != null && !u1.isBlank()) {
            manager.createUser(User.withUsername(u1.trim())
                    .password("{noop}" + p1.trim())
                    .roles("OFFICE", "REPORT")
                    .build());
        }

        if (u2 != null && !u2.isBlank()) {
            manager.createUser(User.withUsername(u2.trim())
                    .password("{noop}" + p2.trim())
                    .roles("EMPLOYEE")
                    .build());
        }

        if (u3 != null && !u3.isBlank()) {
            manager.createUser(User.withUsername(u3.trim())
                    .password("{noop}" + p3.trim())
                    .roles("CUSTOMER", "PAYMENT")
                    .build());
        }

        // Dhanavarshini — PRODUCT + REPORT (was: PRODUCT only)
        if (u4 != null && !u4.isBlank()) {
            manager.createUser(User.withUsername(u4.trim())
                    .password("{noop}" + p4.trim())
                    .roles("PRODUCT", "REPORT")
                    .build());
        }

        // Shivani — ORDER + CUSTOMER (was: ORDER only)
        // CUSTOMER role needed for /api/customers/{customerNumber}/orders
        if (u5 != null && !u5.isBlank()) {
            manager.createUser(User.withUsername(u5.trim())
                    .password("{noop}" + p5.trim())
                    .roles("ORDER", "CUSTOMER")
                    .build());
        }

        return manager;
    }

    // ================= SECURITY CONFIG =================
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable())

                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                )

                .authorizeHttpRequests(auth -> auth

                        // Public resources & Landing page
                        .requestMatchers(
                                "/",
                                "/home",
                                "/images/**",
                                "/css/**",
                                "/js/**",
                                "/favicon.ico"
                        ).permitAll()

                        // Swagger public access
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/webjars/**"
                        ).permitAll()

                        // ── NEW: Aryan's report endpoints ──────────────────
                        .requestMatchers(
                                "/api/reports/customer-exposure",
                                "/api/reports/order-value/**",
                                "/api/reports/sales-by-country",
                                "/api/reports/sales-by-employee"
                        ).hasAnyRole("REPORT", "ADMIN")

                        // ── NEW: Dhana's report endpoints ──────────────────
                        .requestMatchers(
                                "/api/reports/monthly-revenue",
                                "/api/reports/high-risk-customers"
                        ).hasAnyRole("REPORT", "ADMIN")

                        // ── NEW: Shivani's customer-orders endpoint ─────────
                        // Must be BEFORE /api/customers/** rule
                        .requestMatchers("/api/customers/*/orders")
                        .hasAnyRole("ORDER", "CUSTOMER", "ADMIN")

                        // Modules
                        .requestMatchers("/api/customers/**").hasAnyRole("CUSTOMER", "ADMIN")
                        .requestMatchers("/api/products/**").hasAnyRole("PRODUCT", "ADMIN")
                        .requestMatchers("/api/productlines/**").hasAnyRole("PRODUCT", "ADMIN")
                        .requestMatchers("/api/orders/**").hasAnyRole("ORDER", "ADMIN")
                        .requestMatchers("/api/orderdetails/**").hasAnyRole("ORDER", "ADMIN")
                        .requestMatchers("/api/v1/offices/**").hasAnyRole("OFFICE", "ADMIN")
                        .requestMatchers("/api/employees/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers("/employees/**").hasAnyRole("EMPLOYEE", "ADMIN")
                        .requestMatchers("/api/payments/**").hasAnyRole("CUSTOMER", "ADMIN")

                        .anyRequest().authenticated()
                )

                // REQUIRED for Swagger Basic Auth to work
                .httpBasic(Customizer.withDefaults())

                // NO LOGIN POPUP (custom 401 response)
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.setStatus(401);
                            response.setContentType("application/json");
                            response.getWriter().write("{\"error\":\"Unauthorized\"}");
                        })
                );

        return http.build();
    }
}