package com.inventoryBackend.inventoryTest.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class LoggingFilter extends HttpFilter {
    private static final Logger logger = LogManager.getLogger(LoggingFilter.class);

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("Request: {} {}", request.getMethod(), request.getRequestURI());
        chain.doFilter(request, response);
        logger.info("Response: {} {}", response.getStatus(), response);
    }

}
