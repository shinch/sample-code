package com.gmail.shinch.report.authentication;

import com.gmail.shinch.report.code.system.HttpRequestHeaderCode;
import com.gmail.shinch.report.command.BeanUtils;
import com.gmail.shinch.report.exception.UnauthorizedException;
import com.gmail.shinch.report.service.authentication.AuthenticationService;
import com.gmail.shinch.report.service.authentication.CookieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = (HandlerMethod) handler;
            if (method.getMethod().isAnnotationPresent(Permission.class)) {
                Permission permission = method.getMethod().getAnnotation(Permission.class);
                if ( !permission.isLogin() ) {
                    return true;
                }
                // header 에서 사용자 정보를 가져 온다.
                HeaderInfoVo headerInfo = HeaderInfoVo.builder()
                        .userId(request.getHeader(HttpRequestHeaderCode.USER_HEADER.getHeaderName()))
                        .token(request.getHeader(HttpRequestHeaderCode.TOKEN_HEADER.getHeaderName())).build();

                AuthenticationService authenticationService = BeanUtils.getBean("authenticationService", AuthenticationService.class);
                // token 값과 으로 사용자 로그인 정보를 가져 온다.
                UserInfoVo userInfo = authenticationService.getUserInfo(headerInfo);

                if ( !userInfo.isLogin() ) {
                    CookieService cookieService = BeanUtils.getBean("cookieService", CookieService.class);
                    cookieService.setClearCookies(response);
                    authenticationService.clearUserInfoCache(userInfo.getUserId());
                    throw new UnauthorizedException(headerInfo.getUserId());
                }
            }
        }
        return true;
    }
}
