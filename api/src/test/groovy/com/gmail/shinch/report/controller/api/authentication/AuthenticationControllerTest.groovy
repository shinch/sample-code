package com.gmail.shinch.report.controller.api.authentication

import com.gmail.shinch.report.authentication.UserInfoVo
import com.gmail.shinch.report.exception.UnauthorizedException
import com.gmail.shinch.report.facade.authentication.AuthenticationFacade
import com.gmail.shinch.report.facade.authentication.LoginInfoDto
import com.gmail.shinch.report.facade.authentication.UserInfoDto
import com.gmail.shinch.report.service.authentication.CookieService
import spock.lang.Specification

import javax.servlet.http.HttpServletResponse
import javax.validation.ConstraintViolation
import javax.validation.Validation
import javax.validation.executable.ExecutableValidator
import java.lang.reflect.Method

class AuthenticationControllerTest extends Specification {
    AuthenticationController testController
    AuthenticationFacade authenticationFacade
    CookieService cookieService

    ExecutableValidator executableValidator

    def "setup"() {
        authenticationFacade = Mock(AuthenticationFacade)
        cookieService = Mock(CookieService)
        testController = new AuthenticationController(authenticationFacade, cookieService)

        executableValidator = Validation.buildDefaultValidatorFactory().getValidator().forExecutables()
    }

    def "[V1]사용자 로그인 API Spec"() {
        given:
        String tokenString = "tokenString"
        String userId = "userId"
        String password = "password"
        HttpServletResponse response = Mock(HttpServletResponse)
        UserInfoDto userInfoDto = UserInfoDto.builder().login(true).token(tokenString).build()
        when:
        String result = testController.loginV1(userId, password, response)
        then:
        1 * authenticationFacade.getLoginInfo(_ as LoginInfoDto) >> userInfoDto
        1 * cookieService.setLoginCookies(response, userInfoDto)
        result == tokenString
    }

    def "[V1]사용자 로그인 API Exception Spec"() {
        given:
        String tokenString = "tokenString"
        String userId = "userId"
        String password = "password"
        HttpServletResponse response = Mock(HttpServletResponse)
        UserInfoDto userInfoDto = UserInfoDto.builder().login(false).token(tokenString).build()
        when:
        testController.loginV1(userId, password, response)
        then:
        1 * authenticationFacade.getLoginInfo(_ as LoginInfoDto) >> userInfoDto
        0 * cookieService.setLoginCookies(response, userInfoDto)
        thrown(UnauthorizedException)
    }

    def "[V1]사용자 로그인 API Validation spec"() {
        given:
        HttpServletResponse httpServletResponse = Mock(HttpServletResponse)
        expect:
        Method testMethod = testController.getClass().getMethod("loginV1", String.class, String.class, HttpServletResponse.class)
        Object[] params = [password, userId, httpServletResponse]
        Set<ConstraintViolation<AuthenticationController>> violations = executableValidator.validateParameters(testController, testMethod, params)
        validSize == violations.size()
        where:
        userId|password||validSize
        null|null||2
        " "|" "||2
        "userId"|"password"||0
    }

    def "[V1]사용자 로그인 확인 API Validation spec"() {
        given:
        UserInfoVo userInfoVo = Mock(UserInfoVo)
        expect:
        Method testMethod = testController.getClass().getMethod("checkV1", String.class, UserInfoVo.class)
        Object[] params = [userId, userInfoVo]
        Set<ConstraintViolation<AuthenticationController>> violations = executableValidator.validateParameters(testController, testMethod, params)
        validSize == violations.size()
        where:
        userId||validSize
        null||1
        " "||1
        "userId"||0

    }
}
