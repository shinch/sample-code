package com.gmail.shinch.report.facade.authentication;

import com.gmail.shinch.report.dao.database.report.member.MemberEntity;
import com.gmail.shinch.report.dao.database.report.member.MemberRepository;
import com.gmail.shinch.report.service.authentication.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationFacade {
    private final AuthenticationService authenticationService;
    private final MemberRepository memberRepository;

    @Autowired
    public AuthenticationFacade(
            AuthenticationService authenticationService,
            MemberRepository memberRepository ) {
        this.authenticationService = authenticationService;
        this.memberRepository = memberRepository;
    }
    public UserInfoDto getLoginInfo(LoginInfoDto loginInfo) {
        String encPassword = authenticationService.getEncPassword(loginInfo.getUserId(), loginInfo.getPassword());
        loginInfo.setEncPsaaword(encPassword);
        Optional<MemberEntity> optionalMember = memberRepository.findByIdAndPassword(loginInfo.getUserId(), loginInfo.getEncPsaaword());
        if ( optionalMember.isPresent() ) {
            MemberEntity member = optionalMember.get();
            String tokenString =  authenticationService.getToken(loginInfo.getUserId());
            member.setToken(tokenString);
            memberRepository.save(member);
            return UserInfoDto.builder().login(true).token(tokenString).userId(loginInfo.getUserId()).build();
        }
        return UserInfoDto.builder().login(false).token("").userId(loginInfo.getUserId()).build();
    }
}
