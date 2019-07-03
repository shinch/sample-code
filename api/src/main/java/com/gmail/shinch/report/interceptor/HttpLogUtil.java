package com.gmail.shinch.report.interceptor;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HttpLogUtil {
    private final int MAX_PAYLOAD_BYTES = 1024;
    // CHECK : 개인정보를 포함하는 URL을 정의한다.
    private List<String> SERVER_PRIVACIES = Lists.newArrayList("server1", "server2");
    private List<String> CLIENT_PRIVACIES = Lists.newArrayList("client1", "client2");
    private String SERVER_REQUEST_TEMPLATE = "";
    private String SERVER_RESPONSE_TEMPLATE = "";
    private String CLIENT_REQUEST_TEMPLATE = "";
    private String CLIENT_RESPONSE_TEMPLATE = "";
    private String SERVER_API_TEMPLATE = "";
    private String CLIENT_API_TEMPLATE = "";

    private final String PRIVACY_PAYLOAD = "Payload have include privacy data";

    private final Set<String> NO_LOG_PATHS = Collections.unmodifiableSet(new HashSet<>(Arrays.asList("/api/status/status")));

    @PostConstruct
    void initString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("    URI     : {}\n");
        stringBuilder.append("    METHOD  : {}\n");
        stringBuilder.append("    HEADERS : {}\n");
        stringBuilder.append("    PAYLOAD : {}\n");
        SERVER_REQUEST_TEMPLATE = "    ========== SERVER REQUEST BEGIN ==========\n" + stringBuilder.toString() + "    ========== SERVER REQUEST END ==========";
        CLIENT_REQUEST_TEMPLATE = "    ========== CLIENT REQUEST BEGIN ==========\n" + stringBuilder.toString() + "    ========== CLIENT REQUEST END ==========";
        stringBuilder = new StringBuilder();
        stringBuilder.append("    STATUS CODE : {}\n");
        stringBuilder.append("    STATUS TEXT : {}\n");
        stringBuilder.append("    HEADERS     : {}\n");
        stringBuilder.append("    PAYLOAD     : {}\n");
        SERVER_RESPONSE_TEMPLATE = "    ========== SERVER RESPONSE BEGIN ==========\n" + stringBuilder.toString() + "    ========== SERVER RESPONSE END ==========";
        CLIENT_RESPONSE_TEMPLATE = "    ========== CLIENT RESPONSE BEGIN ==========\n" + stringBuilder.toString() + "    ========== CLIENT RESPONSE END ==========";
        stringBuilder = new StringBuilder();
        stringBuilder.append("    ==== REQUEST ====\n");
        stringBuilder.append("    = URI     : {}\n");
        stringBuilder.append("    = METHOD  : {}\n");
        stringBuilder.append("    = HEADERS : {}\n");
        stringBuilder.append("    = PAYLOAD : {}\n");
        stringBuilder.append("    ==== RESPONSE ====\n");
        stringBuilder.append("    = STATUS CODE : {}\n");
        stringBuilder.append("    = STATUS TEXT : {}\n");
        stringBuilder.append("    = HEADERS     : {}\n");
        stringBuilder.append("    = PAYLOAD     : {}\n");
        stringBuilder.append("    ==== SUMMARY ====\n");
        stringBuilder.append("    = START AT  : {}\n");
        stringBuilder.append("    = END AT    : {}\n");
        stringBuilder.append("    = EXECUTION : {}\n");
        SERVER_API_TEMPLATE = "========== SERVER API ==========\n" + stringBuilder.toString();
        CLIENT_API_TEMPLATE = "========== CLIENT API ==========\n" + stringBuilder.toString();
    }

    public void traceServerRequest(ContentCachingRequestWrapper request) {
        boolean isPrivacy = isPrivacy(true, request.getRequestURI());
        String payload = getPayload(request.getContentAsByteArray(), isPrivacy);
        log.info(SERVER_REQUEST_TEMPLATE, request.getRequestURI(), request.getMethod(), getHeaderInfos(request), payload);
    }
    public void traceServerResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        boolean isPrivacy = isPrivacy(true, request.getRequestURI());
        String payload = getPayload(response.getContentAsByteArray(), isPrivacy);
        log.info(SERVER_RESPONSE_TEMPLATE, response.getStatus(), HttpStatus.valueOf(response.getStatus()).getReasonPhrase(), getHeaderInfos(response), payload);
    }
    public void traceServerApi(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response) {
        if ( isLogBypass(request.getRequestURI()) ) {
            return;
        }
        boolean isPrivacy = isPrivacy(true, request.getRequestURI());
        String requestPayload = getPayload(request.getContentAsByteArray(), isPrivacy);
        String responsePayload = getPayload(response.getContentAsByteArray(), isPrivacy);
        long startAt = MDC.get("START_AT") != null ? Long.parseLong(MDC.get("START_AT")) : LocalTime.now().toNanoOfDay();
        long endAt = LocalTime.now().toNanoOfDay();

        LocalTime startNanoTime = null;
        LocalTime endNanoTime = null;
        LocalTime excuteNanoTime = null;
        try {
            startNanoTime = LocalTime.ofNanoOfDay(startAt);
            endNanoTime = LocalTime.ofNanoOfDay(endAt);
            excuteNanoTime = LocalTime.ofNanoOfDay(endAt-startAt);
        } catch ( Exception ex ) {
            log.warn("SERVER API 시간 계산에 실패 하였습니다. S:{} / E:{} / EX:{}",startAt, endAt, endAt - startAt, ex);
        }

        log.info(SERVER_API_TEMPLATE,
                request.getRequestURI(), request.getMethod(), getHeaderInfos(request), requestPayload,
                response.getStatus(), HttpStatus.valueOf(response.getStatus()).getReasonPhrase(), getHeaderInfos(response), responsePayload,
                startNanoTime,
                endNanoTime,
                excuteNanoTime);
    }

    private boolean isLogBypass(String url) {
        for ( String check : NO_LOG_PATHS ) {
            if ( url.contains(check) ) {
                return true;
            }
        }
        return false;
    }
    List<String> getHeaderInfos(ContentCachingRequestWrapper request) {
        List<String> headerInfos = Lists.newArrayList();
        List<String> headerNames = Collections.list(request.getHeaderNames());
        for ( String headerName : headerNames ) {
            headerInfos.add(headerName + " => " + request.getHeader(headerName));
        }
        return headerInfos;
    }

    List<String> getHeaderInfos(ContentCachingResponseWrapper response) {
        List<String> headerInfos = Lists.newArrayList();
        List<String> headerNames = Lists.newArrayList(response.getHeaderNames());
        for ( String headerName : headerNames ) {
            headerInfos.add(headerName + " => " + response.getHeader(headerName));
        }
        return headerInfos;
    }

    List<String> getHeaderInfos(HttpHeaders httpHeaders) {
        List<String> headerNames = httpHeaders.getVary();
        List<String> headerInfos = Lists.newArrayList();
        for ( String headerName : headerNames ) {
            if ( httpHeaders.get(headerName) != null ) {
                headerInfos.add(headerName + " => " + httpHeaders.get(headerName).stream().collect(Collectors.joining(", ")));
            }
        }
        return headerInfos;
    }
    public void traceClientRequest(HttpRequest request, byte[] body) throws IOException {
        String useUrl = request.getURI().toString();
        boolean isPrivacy = isPrivacy(false, useUrl);
        String payload = getPayload(body, isPrivacy);
        log.info(CLIENT_REQUEST_TEMPLATE, useUrl, request.getMethod(), getHeaderInfos(request.getHeaders()), payload);
    }

    public void traceClientResponse(HttpRequest request, ClientHttpResponse response) throws IOException {
        String useUrl = request.getURI().toString();
        boolean isPrivacy = isPrivacy(false, useUrl);
        String payload = getPayload(response.getBody(), isPrivacy);
        log.info(CLIENT_RESPONSE_TEMPLATE, response.getStatusCode(), response.getStatusText(), getHeaderInfos(response.getHeaders()), payload);
    }

    public void traceClientApi(HttpRequest request, byte[] body, ClientHttpResponse response , long startAt) throws IOException {
        String useUrl = request.getURI().toString();
        boolean isPrivacy = isPrivacy(false, useUrl);
        String requestPayload = getPayload(body, isPrivacy);
        String responsePayload = getPayload(response.getBody(), isPrivacy);
        long endAt = LocalTime.now().toNanoOfDay();

        LocalTime startNanoTime = null;
        LocalTime endNanoTime = null;
        LocalTime excuteNanoTime = null;
        try {
            startNanoTime = LocalTime.ofNanoOfDay(startAt);
            endNanoTime = LocalTime.ofNanoOfDay(endAt);
            excuteNanoTime = LocalTime.ofNanoOfDay(endAt-startAt);
        } catch ( Exception ex ) {
            log.warn("CLIENT API 시간 계산에 실패 하였습니다. S:{} / E:{} / EX:{}",startAt, endAt, endAt - startAt, ex);
        }

        log.info(CLIENT_API_TEMPLATE,
                useUrl, request.getMethod(), getHeaderInfos(request.getHeaders()), requestPayload,
                response.getStatusCode(), response.getStatusText(), getHeaderInfos(response.getHeaders()), responsePayload,
                startNanoTime,
                endNanoTime,
                excuteNanoTime);

    }

    boolean isPrivacy(boolean isServer, String url) {
        List<String> privacyUrls = isServer ? SERVER_PRIVACIES : CLIENT_PRIVACIES;
        long count = privacyUrls.stream().filter(excludeApi -> url.contains(excludeApi)).count();
        return count > 0;
    }


    String getPayload(InputStream inStream, boolean isPrivacy) throws IOException {
        if (isPrivacy) {
            return PRIVACY_PAYLOAD;
        }
        StringBuilder inputStringBuilder = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inStream, StandardCharsets.UTF_8));
        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            inputStringBuilder.append(line);
            inputStringBuilder.append('\n');
        }
        byte[] payloads = inputStringBuilder.toString().getBytes(StandardCharsets.UTF_8);
        if ( payloads.length > 2 ) {
            return getPayload(Arrays.copyOfRange(payloads, 0, payloads.length - 1), false);
        }
        return "";
    }

    String getPayload(byte[] inBytes, boolean isPrivacy) {
        if (isPrivacy) {
            return PRIVACY_PAYLOAD;
        }
        if (inBytes.length > MAX_PAYLOAD_BYTES ) {
            return new String(inBytes, 0, MAX_PAYLOAD_BYTES, StandardCharsets.UTF_8);
        } else {
            return new String(inBytes, StandardCharsets.UTF_8);
        }
    }

}
