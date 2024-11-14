package com.macaron.corpresent.domain.auth.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.model.dto.LoginDTO;
import com.macaron.corpresent.domain.auth.model.vo.LoginVO;
import com.macaron.corpresent.domain.auth.service.LoginService;
import com.macaron.corpresent.domain.auth.strategy.LoginStrategy;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.jwt.JwtUtil;
import com.macaron.corpresent.security.context.UserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 18:01
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 获取登录策略
        String loginStrategyBeanName = loginDTO.getLoginType().getName() + LoginStrategy.BASE_NAME;
        LoginStrategy loginStrategy = SpringUtil.getBean(loginStrategyBeanName, LoginStrategy.class);
        User user = loginStrategy.login(loginDTO);
        if(Boolean.TRUE.equals(user.getIsBlocked())) {
            throw new GlobalServiceException(GlobalServiceStatusCode.USER_ACCOUNT_BLOCKED);
        }
        UserHelper userHelper = UserHelper.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
        return LoginVO.builder()
                .token(JwtUtil.createJwt(userHelper))
                .build();
    }
}
