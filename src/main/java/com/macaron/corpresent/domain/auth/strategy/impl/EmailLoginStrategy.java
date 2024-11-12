package com.macaron.corpresent.domain.auth.strategy.impl;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.constants.AuthConstants;
import com.macaron.corpresent.domain.auth.constants.RegisterConstants;
import com.macaron.corpresent.domain.auth.model.dto.EmailDTO;
import com.macaron.corpresent.domain.auth.model.dto.LoginDTO;
import com.macaron.corpresent.domain.auth.model.dto.RegisterDTO;
import com.macaron.corpresent.domain.auth.service.EmailIdentifyService;
import com.macaron.corpresent.domain.auth.service.RegisterService;
import com.macaron.corpresent.domain.auth.strategy.LoginStrategy;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
import com.macaron.corpresent.redis.lock.RedisLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.macaron.corpresent.domain.auth.constants.RegisterConstants.*;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 16:30
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmailLoginStrategy implements LoginStrategy {

    private final EmailIdentifyService emailIdentifyService;

    private final UserService userService;

    private final EmailRegisterStrategy emailRegisterStrategy;

    @Override
    public User login(LoginDTO loginDTO) {
        EmailDTO emailParams = Optional.ofNullable(loginDTO.getEmailParams()).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.PARAM_IS_BLANK));
        String email = emailParams.getEmail();
        emailIdentifyService.validateEmailCode(AuthConstants.EMAIL_LOGIN_IDENTIFY_TYPE, email, emailParams.getCode());
        return userService.getUserByEmail(email).orElseGet(() -> {
            RegisterDTO registerDTO = RegisterDTO.builder()
                    .emailParams(new EmailDTO() {{
                        setEmail(email);
                    }}).build();
            // 这一步没有检查 username，前提是维护了用户名为邮箱格式就必须绑定该邮箱的规则
            return emailRegisterStrategy.register(registerDTO, Boolean.FALSE);
        });
    }

}
