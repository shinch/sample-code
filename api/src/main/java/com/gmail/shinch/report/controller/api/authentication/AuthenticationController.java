package com.gmail.shinch.report.controller.api.authentication;

import com.gmail.shinch.report.authentication.Permission;
import com.gmail.shinch.report.authentication.UserInfoVo;
import com.gmail.shinch.report.exception.UnauthorizedException;
import com.gmail.shinch.report.facade.authentication.AuthenticationFacade;
import com.gmail.shinch.report.facade.authentication.LoginInfoDto;
import com.gmail.shinch.report.facade.authentication.UserInfoDto;
import com.gmail.shinch.report.service.authentication.CookieService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;

@Slf4j
@RestController
@Validated
@RequestMapping("/api/authentication")
@Api(tags = "00-00.사용자 인증 APIs", description = "/api/authentication", produces = "application/json", consumes = "application/json")
public class AuthenticationController {

    private AuthenticationFacade authenticationFacade;
    private CookieService cookieService;

    @Autowired
    public AuthenticationController(
            AuthenticationFacade authenticationFacade,
            CookieService cookieService ) {
        this.authenticationFacade = authenticationFacade;
        this.cookieService = cookieService;
    }
    @Permission(isLogin = false)
    @ApiOperation(value = "사용자 로그인 API", notes = "사용자 인증 Token 생성 후 cookies 설정")
    @PostMapping(value = "/login/{userId}", headers = {"Accept=application/vnd.shinch.api.report-V1+json"})
    @ResponseBody
    public String loginV1(
            @ApiParam(value = "로그인 비밀번호", required = true, example = "password")
            @NotBlank(message = "로그인 비밀번호는 필수 값 입니다.")
            @RequestHeader("X-Api-Password") String password,
            @ApiParam(value = "로그인 ID", required = true, example = "user-01")
            @NotBlank(message = "로그인 ID는 필수 값 입니다.")
            @PathVariable("userId") String userId,
            HttpServletResponse response) {
        LoginInfoDto loginInfo = LoginInfoDto.builder().userId(userId).password(password).build();
        UserInfoDto userInfoDto = authenticationFacade.getLoginInfo(loginInfo);
        if ( userInfoDto.isLogin() ) {
            cookieService.setLoginCookies(response, userInfoDto);
            return userInfoDto.getToken();
        }
        throw new UnauthorizedException(loginInfo.getUserId());
    }

    @Permission
    @ApiOperation(value = "사용자 로그인 확인 API", notes = "사용자 인증 Token이 유효 한지 확인 유효 하지 않을 경우 cookies 삭제")
    @GetMapping(value = "/check/{userId}", headers = {"Accept=application/vnd.shinch.api.report-V1+json"})
    @ResponseBody
    public UserInfoVo checkV1(
            @ApiParam(value = "로그인 ID", required = true, example = "user-01")
            @NotBlank(message = "로그인 ID는 필수 값 입니다.")
            @PathVariable("userId") String userId,
            UserInfoVo uesrInfo ) {
        return uesrInfo;
    }
}
