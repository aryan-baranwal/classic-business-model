package com.classicmodels.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

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
}