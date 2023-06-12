package com.example.library.filters;

import com.example.library.models.ERole;
import com.example.library.models.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RoleFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user.getRole() != ERole.ADMIN) {
            response.sendRedirect("/main");
            return;
        }
        filterChain.doFilter(request, response);
    }
}
