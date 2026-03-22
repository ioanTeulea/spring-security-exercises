package com.example.project.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.logging.Logger;

public class AuthenticationLoggingFIlter extends OncePerRequestFilter {

    private final Logger logger =
            Logger.getLogger(
                    AuthenticationLoggingFIlter.class.getName());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var requestId= request.getHeader("Request-Id");
        logger.info("Successfully authenticated request with id: "+requestId);
        filterChain.doFilter(request, response);
    }
}
