package com.macaron.corpresent.domain.auth.strategy.impl;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.constants.AuthConstants;
import com.macaron.corpresent.domain.auth.model.dto.PasswordDTO;
import com.macaron.corpresent.domain.auth.model.dto.RegisterDTO;
import com.macaron.corpresent.domain.auth.service.PasswordIdentifyService;
import com.macaron.corpresent.domain.auth.strategy.RegisterStrategy;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
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
@RequiredArgsConstructor
@Slf4j
public class PasswordRegisterStrategy implements RegisterStrategy {

    private final UserService userService;

    private final PasswordIdentifyService passwordIdentifyService;

    @Override
    public String getUsername(RegisterDTO registerDTO) {
        String username = Optional.ofNullable(registerDTO.getPasswordParams()).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.PARAM_IS_BLANK)).getUsername();
        if(AuthConstants.EMAIL_PATTERN.matcher(username).matches()) {
            throw new GlobalServiceException("用户名为邮箱格式，必须绑定该邮箱才能注册", GlobalServiceStatusCode.USER_ACCOUNT_REGISTER_ERROR);
        }
        return username;
    }

    @Override
    public User register(RegisterDTO registerDTO, boolean check) {
        PasswordDTO passwordParams = registerDTO.getPasswordParams();
        String username = passwordParams.getUsername();
        UserDTO userDTO = UserDTO.builder()
                .username(username)
                .nickname(username)
                .password(passwordIdentifyService.passwordEncrypt(passwordParams.getPassword()))
                .build();
        return userService.createUser(userDTO);
    }
}
