package com.gmail.shinch.report.interceptor;

import com.gmail.shinch.report.code.system.HttpRequestHeaderCode;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebFilter(urlPatterns = "*")
@Order(1)
public class BootApiHeaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String requestId = String.format("%08X", (0xFFFFFFFF & uuid.hashCode()));
        String userId = httpRequest.getHeader(HttpRequestHeaderCode.USER_HEADER.getHeaderName());
        MDC.put("REQUEST_ID", requestId);
        MDC.put("USER_ID", userId);
        chain.doFilter(request, response);
        MDC.remove("REQUEST_ID");
        MDC.remove("USER_ID");
    }

    @Override
    public void destroy() {

    }
}
