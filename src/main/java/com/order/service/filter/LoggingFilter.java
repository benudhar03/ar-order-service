package com.order.service.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.jboss.logging.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;

        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        log.info("Incoming Request: {} {}", req.getMethod(), req.getRequestURI());
        try {
            chain.doFilter(request, response);
        } finally {
            log.info("Completed Request: {} {}", req.getMethod(), req.getRequestURI());
            MDC.clear();
        }
    }
}