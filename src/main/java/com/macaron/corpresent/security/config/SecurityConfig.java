package com.macaron.corpresent.security.config;

import com.macaron.corpresent.security.authenticate.JwtAuthenticationTokenFilter;
import com.macaron.corpresent.security.authorize.RestfulAuthorizationDeniedHandler;
import com.macaron.corpresent.security.authorize.DynamicResourceAuthorizationManager;
import com.macaron.corpresent.security.authenticate.RestfulAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * SpringSecurity相关配置，仅用于配置SecurityFilterChain
 * Created by macro on 2019/11/5.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final IgnoreUrlsConfig ignoreUrlsConfig;

    private final RestfulAuthorizationDeniedHandler restfulAuthorizationDeniedHandler;

    private final RestfulAuthenticationEntryPoint restfulAuthenticationEntryPoint;

    private final JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    private final DynamicResourceAuthorizationManager dynamicResourceAuthorizationManager;

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(registry -> registry
                // 不需要保护的资源路径允许访问
                .requestMatchers(ignoreUrlsConfig.getUrls().toArray(new String[0])).permitAll()
                // 允许跨域请求的 OPTIONS 请求
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                // 默认任何请求都需要
                // 任何请求都得通过认证鉴权（anyRequest 只能调用一次）
                .anyRequest().access(dynamicResourceAuthorizationManager)
        )
        // 关闭跨站请求防护
        .csrf(AbstractHttpConfigurer::disable)
        // 修改 Session 生成策略为无状态会话
        .sessionManagement(configurer -> configurer
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        )
        // 自定义权限拦截器 JWT 过滤器
        .addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class)
        // 自定义权限拒绝处理类
        .exceptionHandling(configurer -> configurer
                .authenticationEntryPoint(restfulAuthenticationEntryPoint)
                .accessDeniedHandler(restfulAuthorizationDeniedHandler)
        );
        return httpSecurity.build();
    }

}
