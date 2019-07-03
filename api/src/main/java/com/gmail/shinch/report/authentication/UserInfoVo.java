package com.gmail.shinch.report.authentication;

import lombok.Data;

@Data
public class UserInfoVo {
    private String userId;
    private String regToken;
    private boolean login;
}
