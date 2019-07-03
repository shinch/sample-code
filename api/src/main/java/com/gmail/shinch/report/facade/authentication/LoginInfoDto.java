package com.gmail.shinch.report.facade.authentication;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginInfoDto {
    private String userId;
    private String password;
    private String encPsaaword;
    private String token;
}
