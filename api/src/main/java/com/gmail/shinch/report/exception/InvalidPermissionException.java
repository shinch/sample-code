package com.gmail.shinch.report.exception;

import lombok.Getter;

public class InvalidPermissionException extends RuntimeException {
    @Getter
    private String userId;
    @Getter
    private String token;

    public InvalidPermissionException(String userId, String token) {
        super( String.format("정상적이지 않은 인증 정보 [%s/%s] 입니다.", userId, token) );
        this.userId = userId;
        this.token = token;
    }
}
