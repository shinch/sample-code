package com.gmail.shinch.report.authentication;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HeaderInfoVo {
    private String token;
    private String userId;
}
