package com.macaron.corpresent.security.authorize;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.common.util.juc.ThreadLocalMapUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 自定义未登录或者 token 失效时的返回结果
 * Created by macro on 2018/5/14.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RestfulAuthenticationEntryPoint implements AuthenticationEntryPoint {

    public final static String AUTHENTICATION_EXCEPTION_MESSAGE = "Authentication-Exception-Message";

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) {
        log.error(ThreadLocalMapUtil.append(AUTHENTICATION_EXCEPTION_MESSAGE, e.getMessage()));
        handlerExceptionResolver.resolveException(request, response, null, new GlobalServiceException(
                GlobalServiceStatusCode.USER_UN_AUTHORIZED));
    }
}
