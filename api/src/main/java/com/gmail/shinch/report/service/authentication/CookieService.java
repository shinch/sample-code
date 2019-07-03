package com.gmail.shinch.report.service.authentication;

import com.gmail.shinch.report.facade.authentication.UserInfoDto;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class CookieService {
    private final String USER_ID_NAME = "user-id";
    private final String TOKEN_NAME = "token";
    private final String COOKIES_DOMAIN = "localhost";
    private final String COOKIES_PATH = "/";
    private final int MAX_AGE = 60*60*24;

    public void setLoginCookies(HttpServletResponse response, UserInfoDto userInfoDto) {
        Cookie userCookie = new Cookie(USER_ID_NAME, userInfoDto.getUserId());
        userCookie.setDomain(COOKIES_DOMAIN);
        userCookie.setPath(COOKIES_PATH);
        userCookie.setMaxAge(MAX_AGE);
        response.addCookie(userCookie);
        Cookie tokenCookie = new Cookie(TOKEN_NAME, userInfoDto.getToken());
        tokenCookie.setDomain(COOKIES_DOMAIN);
        tokenCookie.setPath(COOKIES_PATH);
        tokenCookie.setMaxAge(MAX_AGE);
        response.addCookie(tokenCookie);
    }
    public void setClearCookies(HttpServletResponse response) {
        Cookie userCookie = new Cookie(USER_ID_NAME, null);
        userCookie.setDomain(COOKIES_DOMAIN);
        userCookie.setPath(COOKIES_PATH);
        userCookie.setMaxAge(0);
        response.addCookie(userCookie);
        Cookie tokenCookie = new Cookie(TOKEN_NAME, null);
        tokenCookie.setDomain(COOKIES_DOMAIN);
        tokenCookie.setPath(COOKIES_PATH);
        tokenCookie.setMaxAge(0);
        response.addCookie(tokenCookie);
    }
}
