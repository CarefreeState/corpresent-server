package com.macaron.corpresent.security.component;

import cn.hutool.core.collection.CollUtil;
import com.macaron.corpresent.common.util.thread.ThreadLocalMapUtil;
import com.macaron.corpresent.security.config.IgnoreUrlsConfig;
import com.macaron.corpresent.security.handler.RestAuthenticationEntryPoint;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 动态鉴权管理器，用于判断是否有资源的访问权限
 * Created by macro on 2023/11/3.
 */
@Component
public class DynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    @Autowired
    private DynamicSecurityMetadataSource securityDataSource;
    @Autowired
    private IgnoreUrlsConfig ignoreUrlsConfig;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext requestAuthorizationContext) {
        HttpServletRequest request = requestAuthorizationContext.getRequest();
        String path = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        //白名单路径直接放行
        List<String> ignoreUrls = ignoreUrlsConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, path)) {
                return new AuthorizationDecision(true);
            }
        }
        //对应跨域的预检请求直接放行
        if(request.getMethod().equals(HttpMethod.OPTIONS.name())){
            return new AuthorizationDecision(true);
        }
        try{
            //权限校验逻辑
            List<ConfigAttribute> configAttributeList = securityDataSource.getConfigAttributesWithPath(path);
            // 约定大于配置，用特殊的规则，让权限校验与接口定义解耦
            // needAuthorities 代表请求路径需要的权限，且只要有其中一个就可以访问
            // 例如 /user/login 需要有 /user/login/**，但资源有 /** /user/** /user/login/**，这三个都是匹配 /user/login
            // 用户有其中任何一个权限，则代表这个路径所匹配的路径的权限
            // 比如用户有访问 /** 的权限，那自然能访问 /user/login
            // 故能匹配本次请求路径，从逻辑上是说得通的
            // 若此集合为空集合，则代表本次请求的资源不存在（哪怕存在这个接口，到这一步也无法判断出来，所以每个接口都应该在数据库里有记录）
            List<String> needAuthorities = configAttributeList.stream()
                    .map(ConfigAttribute::getAttribute)
                    .collect(Collectors.toList());
            Authentication currentAuth = authentication.get();
            //判定是否已经实现登录认证
            if(currentAuth.isAuthenticated()){
                Collection<? extends GrantedAuthority> grantedAuthorities = currentAuth.getAuthorities();
                List<? extends GrantedAuthority> hasAuth = grantedAuthorities.stream()
                        .filter(item -> needAuthorities.contains(item.getAuthority()))
                        .collect(Collectors.toList());
                return new AuthorizationDecision(CollUtil.isNotEmpty(hasAuth));
            }
        } catch(Exception e) {
            ThreadLocalMapUtil.set(RestAuthenticationEntryPoint.AUTHENTICATION_EXCEPTION_MESSAGE, e.getMessage());
        }
        return new AuthorizationDecision(false);
    }

}
