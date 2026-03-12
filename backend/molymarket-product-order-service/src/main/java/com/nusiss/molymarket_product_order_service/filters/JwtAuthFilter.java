package com.nusiss.molymarket_product_order_service.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.nusiss.molymarket_product_order_service.services.JwtService;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;
        String role = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                username = jwtService.extractUsername(token);
                role = jwtService.extractRole(token);
            } catch (Exception e) {
                // Token invalid or expired
            }
        }

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (jwtService.validateToken(token)) {
                List<SimpleGrantedAuthority> authorities = role != null ? 
                    Collections.singletonList(new SimpleGrantedAuthority(role)) : 
                    Collections.emptyList();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    username,
                    null,
                    authorities
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
