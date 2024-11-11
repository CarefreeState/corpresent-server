package com.macaron.corpresent.security.component;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.util.convert.JsonUtil;
import com.macaron.corpresent.handler.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义未登录或者 token 失效时的返回结果
 * Created by macro on 2018/5/14.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("application/json; charset=utf8");
        response.getWriter().println(JsonUtil.toJson(SystemJsonResponse.CUSTOMIZE_MSG_ERROR(GlobalServiceStatusCode.USER_UN_AUTHORIZED, authException.getMessage())));
        globalExceptionHandler.logError(request, response, authException);
        response.getWriter().flush();
    }
}
