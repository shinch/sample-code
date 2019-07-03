package com.gmail.shinch.report.service.authentication;

import com.gmail.shinch.report.authentication.HeaderInfoVo;
import com.gmail.shinch.report.authentication.UserInfoVo;
import com.gmail.shinch.report.dao.database.report.member.MemberEntity;
import com.gmail.shinch.report.dao.database.report.member.MemberRepository;
import com.google.common.hash.Hashing;
import com.google.common.io.BaseEncoding;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class AuthenticationService {
    private final MemberRepository memberRepository;

    public AuthenticationService(
            MemberRepository memberRepository ) {
        this.memberRepository = memberRepository;
    }

    public String getEncPassword(String userId, String password) {
        byte[] encBytes =  Hashing.sha256()
                .hashString(userId + password, StandardCharsets.UTF_8).asBytes();
        return BaseEncoding.base64().encode(encBytes);
    }

    public String getToken(String userId) {
        String ramdom = UUID.randomUUID().toString();
        byte[] ramdomBytes =  Hashing.sha256()
                .hashString(userId + ramdom, StandardCharsets.UTF_8).asBytes();
        return BaseEncoding.base64().encode(ramdomBytes);
    }

    @Cacheable(value="userInfo", key = "#headerInfo.userId")
    public UserInfoVo getUserInfo(HeaderInfoVo headerInfo) {
        UserInfoVo userInfoVo = new UserInfoVo();
        userInfoVo.setUserId(headerInfo.getUserId());
        Optional<MemberEntity> optionalMember = memberRepository.findByIdAndToken(headerInfo.getUserId(), headerInfo.getToken());
        if ( optionalMember.isPresent() ) {
            MemberEntity memberEntity = optionalMember.get();
            userInfoVo.setRegToken(memberEntity.getToken());
            userInfoVo.setLogin(headerInfo.getToken().equals(memberEntity.getToken()) && memberEntity.getTokenExpire().isAfter(LocalDateTime.now()));
        } else {
            userInfoVo.setRegToken("");
            userInfoVo.setLogin(false);
        }
        return userInfoVo;
    }

    @CacheEvict(value="userInfo", key="#userId")
    public void clearUserInfoCache(String userId) {
        log.info("clear userInfo cache");
    }

}
