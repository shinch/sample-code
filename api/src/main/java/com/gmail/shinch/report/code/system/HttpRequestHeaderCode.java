package com.gmail.shinch.report.code.system;

import lombok.Getter;

public enum HttpRequestHeaderCode {
    USER_HEADER("X-Api-User", "로그인 계정 ID"),
    TOKEN_HEADER("X-Api-Token", "인증 Token");

    @Getter
    private String headerName;
    @Getter
    private String description;

    HttpRequestHeaderCode(String headerName, String description) {
        this.headerName = headerName;
        this.description = description;
    }
}
