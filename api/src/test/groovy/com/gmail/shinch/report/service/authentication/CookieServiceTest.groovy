package com.gmail.shinch.report.service.authentication

import com.gmail.shinch.report.facade.authentication.UserInfoDto
import spock.lang.Specification

import javax.servlet.http.Cookie
import javax.servlet.http.HttpServletResponse

class CookieServiceTest extends Specification {
    CookieService testService
    HttpServletResponse response

    def "setup"() {
        testService = new CookieService()
        response = Mock(HttpServletResponse)
    }

    def "로그인 쿠키 등록 spec"() {
        given:
        String userId = "userId"
        String token = "token"
        UserInfoDto userInfoDto = UserInfoDto.builder().userId(userId).token(token).build()
        when:
        testService.setLoginCookies(response, userInfoDto)
        then:
        2 * response.addCookie(_ as Cookie)
    }

    def "로그인 쿠키 종료 spec"() {
        when:
        testService.setClearCookies(response)
        then:
        2 * response.addCookie(_ as Cookie)
    }
}
