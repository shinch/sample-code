package com.gmail.shinch.report.facade.authentication

import com.gmail.shinch.report.dao.database.report.member.MemberEntity
import com.gmail.shinch.report.dao.database.report.member.MemberRepository
import com.gmail.shinch.report.service.authentication.AuthenticationService
import spock.lang.Specification

class AuthenticationFacadeTest extends Specification {
    AuthenticationService authenticationService
    MemberRepository memberRepository
    AuthenticationFacade testFacade

    def "setup"() {
        authenticationService = Mock(AuthenticationService)
        memberRepository = Mock(MemberRepository)
        testFacade = new AuthenticationFacade(authenticationService, memberRepository)
    }

    def "로그인 Facade 성공 spec"() {
        given:
        String userId = "userId"
        String password = "password"
        String tokenString = "tokenString"
        LoginInfoDto loginInfo = LoginInfoDto.builder().userId(userId).password(password).build()
        MemberEntity memberEntity = MemberEntity.builder().build()
        Optional<MemberEntity> optionalMember = Optional.of(memberEntity)
        when:
        UserInfoDto userInfoDto = testFacade.getLoginInfo(loginInfo)
        then:
        1 * authenticationService.getEncPassword(loginInfo.getUserId(), loginInfo.getPassword())
        1 * memberRepository.findByIdAndPassword(loginInfo.getUserId(), loginInfo.getEncPsaaword()) >> optionalMember
        1 * authenticationService.getToken(loginInfo.getUserId()) >> tokenString
        1 * memberRepository.save(memberEntity)
        userInfoDto.login
    }

    def "로그인 Facade 실패 spec"() {
        given:
        String userId = "userId"
        String password = "password"
        LoginInfoDto loginInfo = LoginInfoDto.builder().userId(userId).password(password).build()
        Optional<MemberEntity> optionalMember = Optional.ofNullable(null)
        when:
        UserInfoDto userInfoDto = testFacade.getLoginInfo(loginInfo)
        then:
        1 * authenticationService.getEncPassword(loginInfo.getUserId(), loginInfo.getPassword())
        1 * memberRepository.findByIdAndPassword(loginInfo.getUserId(), loginInfo.getEncPsaaword()) >> optionalMember
        0 * authenticationService.getToken(_ as String)
        0 * memberRepository.save(_ as MemberEntity)
        !userInfoDto.login
    }
}
