package com.classicmodels.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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

    @Autowired
    private ProfileAuthCheckFilter profileAuthCheckFilter;

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

        // Aryan — OFFICE + REPORT
        if (u1 != null && !u1.isBlank()) {
            manager.createUser(User.withUsername(u1.trim())
                    .password("{noop}" + p1.trim())
                    .roles("OFFICE", "REPORT")
                    .build());
        }

        // Aman — EMPLOYEE
        if (u2 != null && !u2.isBlank()) {
            manager.createUser(User.withUsername(u2.trim())
                    .password("{noop}" + p2.trim())
                    .roles("EMPLOYEE")
                    .build());
        }

        // Kushal — CUSTOMER + PAYMENT
        if (u3 != null && !u3.isBlank()) {
            manager.createUser(User.withUsername(u3.trim())
                    .password("{noop}" + p3.trim())
                    .roles("CUSTOMER", "PAYMENT")
                    .build());
        }

        // Dhanavarshini — PRODUCT + REPORT
        if (u4 != null && !u4.isBlank()) {
            manager.createUser(User.withUsername(u4.trim())
                    .password("{noop}" + p4.trim())
                    .roles("PRODUCT", "REPORT")
                    .build());
        }

        // Shivani — ORDER + CUSTOMER
        if (u5 != null && !u5.isBlank()) {
            manager.createUser(User.withUsername(u5.trim())
                    .password("{noop}" + p5.trim())
                    .roles("ORDER", "CUSTOMER")
                    .build());
        }

        return manager;
    }

    // ─────────────────────────────────────────────────────────────
    // SECURITY FILTER CHAINS (ordered, each with its own realm name)
    // Different realm names make the browser store separate credential
    // caches per profile — so credentials from one profile are NEVER
    // auto-sent to another profile's protected area.
    // ─────────────────────────────────────────────────────────────

    /** Employee module — realm: ClassicModels-Employee */
    @Bean @Order(1)
    public SecurityFilterChain employeeChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/employees/**", "/api/employees/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth.anyRequest().hasAnyRole("EMPLOYEE", "ADMIN"))
            .addFilterAfter(profileAuthCheckFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(basic -> basic.realmName("ClassicModels-Employee"))
            .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                res.setStatus(401);
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"Unauthorized\",\"realm\":\"Employee\"}");
            }));
        return http.build();
    }

    /** Customer module — realm: ClassicModels-Customer */
    @Bean @Order(2)
    public SecurityFilterChain customerChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/customers/**", "/api/customers/**", "/api/payments/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/customers/*/orders").hasAnyRole("ORDER", "CUSTOMER", "ADMIN")
                .anyRequest().hasAnyRole("CUSTOMER", "ADMIN"))
            .addFilterAfter(profileAuthCheckFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(basic -> basic.realmName("ClassicModels-Customer"))
            .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                res.setStatus(401);
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"Unauthorized\",\"realm\":\"Customer\"}");
            }));
        return http.build();
    }

    /** Office module — realm: ClassicModels-Office */
    @Bean @Order(3)
    public SecurityFilterChain officeChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/offices/**", "/api/v1/offices/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth.anyRequest().hasAnyRole("OFFICE", "ADMIN"))
            .addFilterAfter(profileAuthCheckFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(basic -> basic.realmName("ClassicModels-Office"))
            .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                res.setStatus(401);
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"Unauthorized\",\"realm\":\"Office\"}");
            }));
        return http.build();
    }

    /** Product module — realm: ClassicModels-Product */
    @Bean @Order(4)
    public SecurityFilterChain productChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/products/**", "/api/products/**", "/api/productlines/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth.anyRequest().hasAnyRole("PRODUCT", "ADMIN"))
            .addFilterAfter(profileAuthCheckFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(basic -> basic.realmName("ClassicModels-Product"))
            .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                res.setStatus(401);
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"Unauthorized\",\"realm\":\"Product\"}");
            }));
        return http.build();
    }

    /** Order module — realm: ClassicModels-Order */
    @Bean @Order(5)
    public SecurityFilterChain orderChain(HttpSecurity http) throws Exception {
        http
            .securityMatcher("/orders/**", "/api/orders/**", "/api/orderdetails/**")
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth.anyRequest().hasAnyRole("ORDER", "ADMIN"))
            .addFilterAfter(profileAuthCheckFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(basic -> basic.realmName("ClassicModels-Order"))
            .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                res.setStatus(401);
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"Unauthorized\",\"realm\":\"Order\"}");
            }));
        return http.build();
    }

    /** Reports + catch-all (public pages, Swagger, auth status) — realm: ClassicModels-Portal */
    @Bean @Order(6)
    public SecurityFilterChain defaultChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
            .authorizeHttpRequests(auth -> auth

                // Public resources & landing page
                .requestMatchers(
                    "/", "/home",
                    "/images/**", "/css/**", "/js/**",
                    "/favicon.ico", "/logout",
                    "/api/auth/validate"
                ).permitAll()

                // Swagger public access
                .requestMatchers(
                    "/swagger-ui/**", "/swagger-ui.html",
                    "/v3/api-docs/**", "/swagger-resources/**", "/webjars/**"
                ).permitAll()

                // Reports
                .requestMatchers(
                    "/api/reports/customer-exposure",
                    "/api/reports/order-value/**",
                    "/api/reports/sales-by-country",
                    "/api/reports/sales-by-employee"
                ).hasAnyRole("REPORT", "ADMIN")
                .requestMatchers(
                    "/api/reports/monthly-revenue",
                    "/api/reports/high-risk-customers"
                ).hasAnyRole("REPORT", "ADMIN")

                // Auth status endpoint — any authenticated user
                .requestMatchers("/api/auth/status").authenticated()

                .anyRequest().authenticated()
            )
            .httpBasic(basic -> basic.realmName("ClassicModels-Portal"))
            .exceptionHandling(ex -> ex.authenticationEntryPoint((req, res, e) -> {
                res.setStatus(401);
                res.setContentType("application/json");
                res.getWriter().write("{\"error\":\"Unauthorized\"}");
            }));
        return http.build();
    }
}
