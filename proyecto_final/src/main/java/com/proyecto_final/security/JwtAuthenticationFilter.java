package com.proyecto_final.security;

import java.io.IOException;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    // a esta no li aplica filtros
    @Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {
    if (request.getRequestURI().equals("/login")) {
        filterChain.doFilter(request, response); 
        return;
    }
    // agarra el token y el valida
    String token = jwtTokenProvider.getTokenFromRequest(request);
    if (token != null && jwtTokenProvider.validateToken(token)) {
        SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
    }
    filterChain.doFilter(request, response);
}

}
