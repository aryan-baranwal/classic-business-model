package com.classicmodels.controller;

import com.classicmodels.dto.AuthValidationRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder =
            PasswordEncoderFactories.createDelegatingPasswordEncoder();

    public HomeController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/api/auth/status")
    @ResponseBody
    public Map<String, Object> authStatus(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Object> status = new HashMap<>();
        if (userDetails != null) {
            status.put("authenticated", true);
            status.put("username", userDetails.getUsername());
            status.put("roles", userDetails.getAuthorities().stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList()));
        } else {
            status.put("authenticated", false);
        }
        return status;
    }

    @PostMapping("/api/auth/validate")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> validateAuth(
            @Valid @RequestBody AuthValidationRequest request,
            HttpServletRequest servletRequest
    ) {

        Map<String, Object> response = new HashMap<>();
        String username = request.getUsername().trim();
        String password = request.getPassword().trim();

        try {
            UserDetails userDetails =
                    userDetailsService.loadUserByUsername(username);

            if (!passwordEncoder.matches(
                    password,
                    userDetails.getPassword()
            )) {
                response.put("authenticated", false);
                response.put("message", "Invalid username or password");
                return ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body(response);
            }

            List<String> authorities = userDetails.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toList());

            boolean allowedProfile = Arrays.stream(
                            request.getRoles().split(",")
                    )
                    .map(String::trim)
                    .filter(role -> !role.isBlank())
                    .anyMatch(role -> authorities.contains("ROLE_" + role)
                            || authorities.contains("ROLE_ADMIN"));

            if (!allowedProfile) {
                SecurityContextHolder.clearContext();
                response.put("authenticated", true);
                response.put("authorized", false);
                response.put("username", userDetails.getUsername());
                response.put("roles", authorities);
                response.put(
                        "message",
                        "This user is not allowed to access the selected profile"
                );
                return ResponseEntity
                        .status(HttpStatus.FORBIDDEN)
                        .body(response);
            }

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities()
                    );
            SecurityContext securityContext =
                    SecurityContextHolder.createEmptyContext();
            securityContext.setAuthentication(authentication);
            SecurityContextHolder.setContext(securityContext);
            servletRequest.getSession(true).setAttribute(
                    HttpSessionSecurityContextRepository
                            .SPRING_SECURITY_CONTEXT_KEY,
                    securityContext
            );

            response.put("authenticated", true);
            response.put("authorized", true);
            response.put("username", userDetails.getUsername());
            response.put("roles", authorities);

            return ResponseEntity.ok(response);
        } catch (UsernameNotFoundException exception) {
            response.put("authenticated", false);
            response.put("message", "Invalid username or password");
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(response);
        }
    }
}
