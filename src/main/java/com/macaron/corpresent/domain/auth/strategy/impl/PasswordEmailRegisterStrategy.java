package com.macaron.corpresent.domain.auth.strategy.impl;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.constants.RegisterConstants;
import com.macaron.corpresent.domain.auth.util.PasswordMd5WithSaltUtil;
import com.macaron.corpresent.domain.auth.constants.AuthConstants;
import com.macaron.corpresent.domain.auth.model.dto.EmailDTO;
import com.macaron.corpresent.domain.auth.model.dto.PasswordDTO;
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

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 23:26
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PasswordEmailRegisterStrategy implements RegisterStrategy {

    private final RedisLock redisLock;

    private final EmailIdentifyService emailIdentifyService;

    private final EmailRegisterStrategy emailRegisterStrategy;

    private final PasswordRegisterStrategy passwordRegisterStrategy;

    private final UserService userService;

    @Override
    public String getUsername(RegisterDTO registerDTO) {
        String email = emailRegisterStrategy.getUsername(registerDTO);
        String username = passwordRegisterStrategy.getUsername(registerDTO);
        if(AuthConstants.EMAIL_PATTERN.matcher(username).matches() && !username.equals(email)) {
            throw new GlobalServiceException("用户名为邮箱格式，必须绑定该邮箱才能注册", GlobalServiceStatusCode.USER_ACCOUNT_REGISTER_ERROR);
        }
        return username;
    }

    @Override
    public User register(RegisterDTO registerDTO, boolean check) {
        EmailDTO emailParams = registerDTO.getEmailParams();
        String email = emailParams.getEmail();
        if(Boolean.TRUE.equals(check)) {
            // 验证邮箱
            emailIdentifyService.validateEmailCode(AuthConstants.EMAIL_REGISTER_IDENTIFY_TYPE, email, emailParams.getCode());
        }
        PasswordDTO passwordParams = registerDTO.getPasswordParams();
        return redisLock.tryLockGetSomething(RegisterConstants.REGISTER_EMAIL_LOCK + email, () -> {
            userService.getUserByEmail(email).ifPresent(user -> {
                String message = String.format("邮箱： %s 已存在", email);
                throw new GlobalServiceException(message, GlobalServiceStatusCode.USER_ACCOUNT_ALREADY_EXIST);
            });
            UserDTO userDTO = UserDTO.builder()
                    .username(email)
                    .nickname(email)
                    .email(email)
                    .password(PasswordMd5WithSaltUtil.encrypt(passwordParams.getPassword()))
                    .build();
            return userService.createUser(userDTO);
        }, () -> null);
    }
}
