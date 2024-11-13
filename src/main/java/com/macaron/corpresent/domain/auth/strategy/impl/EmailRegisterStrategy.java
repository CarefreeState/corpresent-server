package com.macaron.corpresent.domain.auth.strategy.impl;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.constants.RegisterConstants;
import com.macaron.corpresent.domain.auth.enums.EmailIdentifyType;
import com.macaron.corpresent.domain.auth.model.dto.EmailDTO;
import com.macaron.corpresent.domain.auth.model.dto.RegisterDTO;
import com.macaron.corpresent.domain.auth.service.EmailIdentifyService;
import com.macaron.corpresent.domain.auth.strategy.RegisterStrategy;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
import com.macaron.corpresent.redis.lock.RedisLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 23:25
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailRegisterStrategy implements RegisterStrategy {

    private final RedisLock redisLock;

    private final EmailIdentifyService emailIdentifyService;

    private final UserService userService;

    @Override
    public String getUsername(RegisterDTO registerDTO) {
        return Optional.ofNullable(registerDTO.getEmailParams()).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.PARAM_IS_BLANK)).getEmail();
    }

    @Override
    public User register(RegisterDTO registerDTO, boolean check) {
        EmailDTO emailParams = registerDTO.getEmailParams();
        String email = emailParams.getEmail();
        if(Boolean.TRUE.equals(check)) {
            // 验证邮箱
            emailIdentifyService.validateEmailCode(EmailIdentifyType.REGISTER, email, emailParams.getCode());
        }
        return redisLock.tryLockGetSomething(RegisterConstants.REGISTER_EMAIL_LOCK + email, () -> {
            userService.getUserByEmail(email).ifPresent(user -> {
                String message = String.format("邮箱： %s 已存在", email);
                throw new GlobalServiceException(message, GlobalServiceStatusCode.USER_ACCOUNT_ALREADY_EXIST);
            });
            UserDTO userDTO = UserDTO.builder()
                    .username(email)
                    .nickname(email)
                    .email(email)
                    .build();
            return userService.createUser(userDTO);
        }, () -> null);
    }
}
