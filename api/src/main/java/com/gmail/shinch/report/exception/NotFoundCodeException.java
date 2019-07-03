package com.gmail.shinch.report.exception;

import lombok.Getter;

public class NotFoundCodeException extends RuntimeException {
    @Getter
    private final String typeName;
    @Getter
    private final String typeCode;

    public NotFoundCodeException(String typeName, String typeCode) {
        super(typeName + " enum에서 " + typeCode + " 를 찾을 수 없습니다");
        this.typeName = typeName;
        this.typeCode = typeCode;
    }
}
