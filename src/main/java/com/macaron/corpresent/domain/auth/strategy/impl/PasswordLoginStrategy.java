package com.macaron.corpresent.domain.auth.strategy.impl;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.model.dto.LoginDTO;
import com.macaron.corpresent.domain.auth.model.dto.PasswordDTO;
import com.macaron.corpresent.domain.auth.service.PasswordIdentifyService;
import com.macaron.corpresent.domain.auth.strategy.LoginStrategy;
import com.macaron.corpresent.domain.user.model.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
public class PasswordLoginStrategy implements LoginStrategy {

    private final PasswordIdentifyService passwordIdentifyService;

    @Override
    public User login(LoginDTO loginDTO) {
        PasswordDTO passwordParams = Optional.ofNullable(loginDTO.getPasswordParams()).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.PARAM_IS_BLANK));
        return passwordIdentifyService.validatePassword(passwordParams.getUsername(), passwordParams.getPassword());
    }
}
