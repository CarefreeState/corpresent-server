package com.macaron.corpresent.security.component;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.util.convert.JsonUtil;
import com.macaron.corpresent.handler.GlobalExceptionHandler;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 自定义无权限访问的返回结果
 * Created by macro on 2018/4/26.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class RestfulAccessDeniedHandler implements AccessDeniedHandler{

    private final GlobalExceptionHandler globalExceptionHandler;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException e) throws IOException {
        response.setHeader("Cache-Control","no-cache");
        response.setContentType("application/json; charset=utf8");
        response.getWriter().println(JsonUtil.toJson(SystemJsonResponse.CUSTOMIZE_MSG_ERROR(GlobalServiceStatusCode.USER_NO_PERMISSION, e.getMessage())));
        globalExceptionHandler.logError(request, response, e);
        response.getWriter().flush();
    }
}
