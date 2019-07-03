package com.gmail.shinch.report.exception;

import lombok.Getter;

public class DenyPermissionException extends RuntimeException {
    @Getter
    private String userId;

    public DenyPermissionException(boolean isDeny, String userId, String permissionName) {
        super( String.format((isDeny ? "[%] 사용자는 [%]권한 으로 허가 되지 않습니다." : "[%] 사용자는 [%]권한을 소유 하지 않았음으로 허가 되지 않습니다."), userId, permissionName) );
        this.userId = userId;
    }
}
