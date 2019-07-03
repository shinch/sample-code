package com.gmail.shinch.report.exception;

import lombok.Getter;

public class UnauthorizedException extends RuntimeException {
    @Getter
    private String userId;

    public UnauthorizedException(String userId) {
        super( String.format("[%s] 사용자 인증에 실패하였습니다.", userId) );
        this.userId = userId;
    }
}
