package com.gmail.shinch.report.service.authentication

import com.gmail.shinch.report.authentication.HeaderInfoVo
import com.gmail.shinch.report.authentication.UserInfoVo
import com.gmail.shinch.report.dao.database.report.member.MemberEntity
import com.gmail.shinch.report.dao.database.report.member.MemberRepository
import groovy.util.logging.Slf4j
import spock.lang.Specification

import java.time.LocalDateTime

@Slf4j
class AuthenticationServiceTest extends Specification {
    AuthenticationService testService
    MemberRepository memberRepository

    def "setup"() {
        memberRepository = Mock(MemberRepository)
        testService = new AuthenticationService(memberRepository)
    }

    def "비밀번호 암호화 Spec"() {
        given:
        String password = "password"
        expect:
        encPassword == testService.getEncPassword(userId, password)
        where:
        userId||encPassword
        'user-01'||'1CsHRfQPVzSA46EDPaZhCRQC0f0SmeQRf58m1Flttrc='
        'user-02'||'lVR8SmO8Zr7LB/D8/6XoX8cyX228gC+INLK7Z3fz484='
        'user-03'||'01qYFixVHnlkkq8vvIe0pYZc/TuwoSbberMbPc4TvFY='
        'user-04'||'Jm/f9GLurSL+ouG1h7+4Y1gdvXtXCz8JUbulna+ZQWI='
        'uesr-05'||'dvsA6A8+Lgy4RXtvEiZMhh4tY/Tt0IcHBjtOyIoHmDE='
    }

    def "인증토큰 생성 Spec"() {
        given:
        String userId = "user-01"
        when:
        String token = testService.getToken(userId)
        log.info("result : {}", token)
        then:
        token.size() == 44
    }

    def "로그인 유지 사용자 정보 반환 Spec"() {
        given:
        String userId = "userId"
        String token = "token"
        HeaderInfoVo headerInfo = HeaderInfoVo.builder().userId(userId).token(token).build()
        MemberEntity memberEntity = MemberEntity.builder().id(userId).token(token).tokenExpire(LocalDateTime.now().plusDays(1L)).build()
        Optional<MemberEntity> optionalMemberEntity = Optional.of(memberEntity)
        when:
        UserInfoVo result = testService.getUserInfo(headerInfo)
        then:
        1 * memberRepository.findByIdAndToken(headerInfo.getUserId(), headerInfo.getToken()) >> optionalMemberEntity
        result.login
        result.regToken == token
    }

    def "로그인 만료 사용자 정보 반환 Spec"() {
        given:
        String userId = "userId"
        String token = "token"
        HeaderInfoVo headerInfo = HeaderInfoVo.builder().userId(userId).token(token).build()
        Optional<MemberEntity> optionalMemberEntity = Optional.empty()
        when:
        UserInfoVo result = testService.getUserInfo(headerInfo)
        then:
        1 * memberRepository.findByIdAndToken(headerInfo.getUserId(), headerInfo.getToken()) >> optionalMemberEntity
        !result.login
        result.regToken == ""
    }
}
