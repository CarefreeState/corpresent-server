package com.macaron.corpresent.security.authorize;

import com.macaron.corpresent.common.util.http.HttpRequestUtil;
import com.macaron.corpresent.common.util.juc.ThreadLocalMapUtil;
import com.macaron.corpresent.security.authenticate.RestfulAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

/**
 * 动态鉴权管理器，用于判断是否有资源的访问权限
 * Created by macro on 2023/11/3.
 */
@Component
@RequiredArgsConstructor
public class DynamicResourceAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String path = request.getRequestURI();
        try{
            Authentication currentAuth = authentication.get();
            // 判定是否已经实现认证
            if(currentAuth.isAuthenticated()){
                // 通过用户现有的资源权限，判断是否可以访问当前请求路径
                boolean granted = currentAuth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .anyMatch(pattern -> HttpRequestUtil.matchPath(pattern, path));
                return new AuthorizationDecision(granted);
            }
        } catch(Exception e) {
            ThreadLocalMapUtil.set(RestfulAuthorizationDeniedHandler.ACCESS_DENIED_EXCEPTION_MESSAGE, e.getMessage());
        }
        return new AuthorizationDecision(Boolean.FALSE);
    }

}
