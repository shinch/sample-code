package com.gmail.shinch.report.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;

@Slf4j
@WebFilter(urlPatterns = "/api/*")
@Order(2)
public class BootApiLogFilter implements Filter {

    private HttpLogUtil httpLogUtil;

    @Autowired
    public BootApiLogFilter(
            HttpLogUtil httpLogUtil ) {
        this.httpLogUtil = httpLogUtil;
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper((HttpServletRequest)request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper((HttpServletResponse)response);
        MDC.put("START_AT", Long.toString(LocalTime.now().toNanoOfDay()) );
        chain.doFilter(requestWrapper, responseWrapper);
        httpLogUtil.traceServerApi(requestWrapper, responseWrapper);
        MDC.remove("START_AT");
        responseWrapper.copyBodyToResponse();

    }

    @Override
    public void destroy() {

    }
}
