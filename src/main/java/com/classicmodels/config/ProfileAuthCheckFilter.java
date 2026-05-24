package com.classicmodels.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ProfileAuthCheckFilter — enforces profile-level role isolation.
 *
 * If a user is authenticated but their role does NOT match the required role
 * for the requested URL prefix, this filter returns HTTP 403 Forbidden
 * with a JSON body. This prevents a user logged in as Profile A from
 * accessing Profile B's pages in another browser tab.
 */
@Component
public class ProfileAuthCheckFilter extends OncePerRequestFilter {

    /**
     * URL prefix → required Spring Security role name (without ROLE_ prefix).
     * Order matters — more specific paths first.
     */
    private static final Map<String, String> PREFIX_ROLE_MAP = new LinkedHashMap<>();

    static {
        // Employee module — only ROLE_EMPLOYEE
        PREFIX_ROLE_MAP.put("/employees",          "EMPLOYEE");
        PREFIX_ROLE_MAP.put("/api/employees",      "EMPLOYEE");

        // Customer module — only ROLE_CUSTOMER
        PREFIX_ROLE_MAP.put("/customers",          "CUSTOMER");
        PREFIX_ROLE_MAP.put("/api/customers",      "CUSTOMER");
        PREFIX_ROLE_MAP.put("/api/payments",       "CUSTOMER");

        // Office module — only ROLE_OFFICE
        PREFIX_ROLE_MAP.put("/offices",            "OFFICE");
        PREFIX_ROLE_MAP.put("/api/v1/offices",     "OFFICE");

        // Product module — only ROLE_PRODUCT
        PREFIX_ROLE_MAP.put("/products",           "PRODUCT");
        PREFIX_ROLE_MAP.put("/api/products",       "PRODUCT");
        PREFIX_ROLE_MAP.put("/api/productlines",   "PRODUCT");

        // Order module — only ROLE_ORDER
        PREFIX_ROLE_MAP.put("/orders",             "ORDER");
        PREFIX_ROLE_MAP.put("/api/orders",         "ORDER");
        PREFIX_ROLE_MAP.put("/api/orderdetails",   "ORDER");

        // Reports — accessible by REPORT or ADMIN role (multi-role, handled below)
        PREFIX_ROLE_MAP.put("/api/reports",        "REPORT");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String path = request.getRequestURI();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // Only check authenticated, non-anonymous users
        if (auth != null && auth.isAuthenticated()
                && !"anonymousUser".equals(auth.getPrincipal())) {

            String requiredRole = getRequiredRoleFor(path);

            if (requiredRole != null) {
                Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
                boolean hasRole = authorities.stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(a -> a.equals("ROLE_" + requiredRole)
                                    || a.equals("ROLE_ADMIN"));

                if (!hasRole) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.setContentType("application/json;charset=UTF-8");
                    response.getWriter().write(
                        "{\"error\":\"Access Denied\"," +
                        "\"message\":\"You are authenticated as a different profile. " +
                        "Please return to the portal and log in with the correct profile.\"," +
                        "\"requiredRole\":\"" + requiredRole + "\"," +
                        "\"authenticatedUser\":\"" + auth.getName() + "\"}"
                    );
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    /**
     * Finds the required role for the given request path by checking URL prefixes.
     * Returns null if no role restriction applies (public paths, etc.).
     */
    private String getRequiredRoleFor(String path) {
        for (Map.Entry<String, String> entry : PREFIX_ROLE_MAP.entrySet()) {
            if (path.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }
}
