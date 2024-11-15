package com.macaron.corpresent.security.authenticate;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.common.util.juc.ThreadLocalMapUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerExceptionResolver;

/**
 * 自定义无权限访问的返回结果
 * Created by macro on 2018/4/26.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class RestfulAccessDeniedHandler implements AccessDeniedHandler {

    public final static String ACCESS_DENIED_EXCEPTION_MESSAGE = "Access-Denied_Exception-Message";

    private final HandlerExceptionResolver handlerExceptionResolver;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) {
        log.error(ThreadLocalMapUtil.append(ACCESS_DENIED_EXCEPTION_MESSAGE, e.getMessage()));
        handlerExceptionResolver.resolveException(request, response, null, new GlobalServiceException(
                GlobalServiceStatusCode.USER_NO_PERMISSION)
        );
    }
}
