package com.example.retail_system_api.config;

import com.example.retail_system_api.service.user.CustomUserService;
import com.example.retail_system_api.service.user.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwaGFuaXRoQGdtYWlsLmNvbSIsImlhdCI6MTc2NzMzNzg4MCwiZXhwIjoxNzY3MzQxNDgwfQ.430iTcOXj0k3xoCTEGoNLAaCGn3VEbbczgu5mr7-yHs
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String emailPayload = null; //data or payload in Token

        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            emailPayload = jwtService.extractEmail(token);
        }

        if(emailPayload != null && SecurityContextHolder.getContext().getAuthentication() == null){
            UserDetails userDetails = context.getBean(CustomUserService.class).loadUserByUsername(emailPayload);
            if(jwtService.validateToken(token, userDetails)){
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}