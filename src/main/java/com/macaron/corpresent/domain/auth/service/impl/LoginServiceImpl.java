package com.macaron.corpresent.domain.auth.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.common.util.convert.JsonUtil;
import com.macaron.corpresent.domain.auth.model.dto.LoginDTO;
import com.macaron.corpresent.domain.auth.model.vo.LoginVO;
import com.macaron.corpresent.domain.auth.service.LoginService;
import com.macaron.corpresent.domain.auth.strategy.LoginStrategy;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.jwt.JwtUtil;
import com.macaron.corpresent.jwt.UserHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 18:01
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 获取登录策略
        LoginStrategy loginStrategy = SpringUtil.getBean(loginDTO.getLoginType() + LoginStrategy.BASE_NAME, LoginStrategy.class);
        User user = loginStrategy.login(loginDTO);
        UserHelper userHelper = UserHelper.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
        return LoginVO.builder()
                .token(JwtUtil.createJwt(JsonUtil.toJson(userHelper)))
                .build();
    }
}