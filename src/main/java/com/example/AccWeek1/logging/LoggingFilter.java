package com.example.AccWeek1.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;
import jakarta.servlet.Filter;
import java.util.logging.LogRecord;

@Component
public class LoggingFilter implements Filter {

    private static final String TRACE_ID = "traceId";


    @Override
    public void doFilter
            (ServletRequest request,
             ServletResponse response,
             FilterChain chain)
            throws IOException, ServletException
    {
        try {
            String traceId = UUID.randomUUID().toString();
            MDC.put(TRACE_ID, traceId);

            if (request instanceof HttpServletRequest httpRequest) {
                MDC.put("method", httpRequest.getMethod());
                MDC.put("uri", httpRequest.getRequestURI());
                MDC.put("clientIP", httpRequest.getRemoteAddr());
            }

            chain.doFilter(request, response);
        }

        finally {
            MDC.clear();
        }
    }
}
