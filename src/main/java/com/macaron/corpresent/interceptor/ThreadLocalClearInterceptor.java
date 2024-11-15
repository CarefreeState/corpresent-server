package com.macaron.corpresent.interceptor;

import com.macaron.corpresent.common.util.juc.ThreadLocalMapUtil;
import com.macaron.corpresent.config.RequestIdConfig;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Optional;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-05-29
 * Time: 10:59
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ThreadLocalClearInterceptor implements HandlerInterceptor {

    private final RequestIdConfig requestIdConfig;

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        try {
            String requestId = response.getHeader(requestIdConfig.getHeader());
            log.info("请求 {} 访问接口 {} {}，响应 HTTP 状态码 {}，错误信息 {}",
                    requestId, request.getMethod(), request.getRequestURI(), response.getStatus(),
                    Optional.ofNullable(ex).map(Exception::getMessage).orElse(null)
            );
        }finally {
            ThreadLocalMapUtil.removeAll();
        }
    }
}
