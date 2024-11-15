package com.macaron.corpresent.security.authenticate;

import com.macaron.corpresent.common.util.http.SnowflakeIdGenerator;
import com.macaron.corpresent.common.util.juc.ThreadLocalMapUtil;
import com.macaron.corpresent.config.RequestIdConfig;
import com.macaron.corpresent.jwt.JwtUtil;
import com.macaron.corpresent.security.authorize.RestfulAuthorizationDeniedHandler;
import com.macaron.corpresent.security.context.BaseContext;
import com.macaron.corpresent.security.context.UserHelper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT登录授权过滤器
 * Created by macro on 2018/4/26.
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    private final RequestIdConfig requestIdConfig;

    private final SnowflakeIdGenerator requestIdGenerator;

    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        // 设置请求 id
        long requestId = requestIdGenerator.nextId();
        response.setHeader(requestIdConfig.getHeader(), String.valueOf(requestId));
        try {
            // 解析 token
            UserHelper userHelper = JwtUtil.parseJwt(request, response, UserHelper.class);
            String username = userHelper.getUsername();
            log.info("checking username:{}", username);
            if (StringUtils.hasText(username)) {
                // 加载用户详情
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                PreAuthenticatedAuthenticationToken authentication = new PreAuthenticatedAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                log.info("authenticated user:{}", username);
                SecurityContextHolder.getContext().setAuthentication(authentication); // 覆盖上下文
                // 设置本地线程
                BaseContext.setCurrentUser(userHelper);
            }
        } catch (Exception e) {
            ThreadLocalMapUtil.set(RestfulAuthenticationEntryPoint.AUTHENTICATION_EXCEPTION_MESSAGE, e.getMessage());
        }
        chain.doFilter(request, response);
    }
}
