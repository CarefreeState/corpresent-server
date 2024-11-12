package com.macaron.corpresent.domain.auth.service.impl;

import cn.hutool.extra.spring.SpringUtil;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.constants.RegisterConstants;
import com.macaron.corpresent.domain.auth.model.dto.RegisterDTO;
import com.macaron.corpresent.domain.auth.service.RegisterService;
import com.macaron.corpresent.domain.auth.strategy.RegisterStrategy;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
import com.macaron.corpresent.redis.lock.RedisLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 18:30
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {

    private final RedisLock redisLock;

    private final UserService userService;

    @Override
    public User register(RegisterDTO registerDTO) {
        String registerStrategyBeanName = registerDTO.getRegisterType() + RegisterStrategy.BASE_NAME;
        RegisterStrategy registerStrategy = SpringUtil.getBean(registerStrategyBeanName, RegisterStrategy.class);
        String username = registerStrategy.getUsername(registerDTO);
        // 加锁尝试注册
        return redisLock.tryLockGetSomething(RegisterConstants.REGISTER_USERNAME_LOCK + username, () -> {
            userService.getUserByUsername(username).ifPresent(user -> {
                String message = String.format("用户名： %s 已存在", username);
                throw new GlobalServiceException(message, GlobalServiceStatusCode.USER_ACCOUNT_ALREADY_EXIST);
            });
            return registerStrategy.register(registerDTO, Boolean.TRUE);
        }, () -> null);
    }
}
