package com.example.project.filters;

import jakarta.servlet.*;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;
@Component
public class CsrfTokenLogger implements Filter {

    private Logger logger = Logger.getLogger(CsrfTokenLogger.class.getName());
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if(servletRequest.getAttribute("_csrf") != null) {
            CsrfToken token = (CsrfToken) servletRequest.getAttribute("_csrf");
            logger.info("CSRF token: " + token.getToken());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
