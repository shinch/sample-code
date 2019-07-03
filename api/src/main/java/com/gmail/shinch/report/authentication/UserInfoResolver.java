package com.gmail.shinch.report.authentication;

import com.gmail.shinch.report.code.system.HttpRequestHeaderCode;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

public class UserInfoResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(UserInfoVo.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        // Header를 읽어서 UserInfo 정보를 등록 한다
        UserInfoVo uesrInfo = new UserInfoVo();
        uesrInfo.setUserId(httpServletRequest.getHeader(HttpRequestHeaderCode.USER_HEADER.getHeaderName()));
        uesrInfo.setRegToken(httpServletRequest.getHeader(HttpRequestHeaderCode.TOKEN_HEADER.getHeaderName()));
        uesrInfo.setLogin(true);
        return uesrInfo;
    }
}
