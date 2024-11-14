package com.macaron.corpresent.domain.auth.controller;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.domain.auth.enums.EmailIdentifyType;
import com.macaron.corpresent.domain.auth.model.dto.EmailIdentifyDTO;
import com.macaron.corpresent.domain.auth.model.dto.FindPasswordDTO;
import com.macaron.corpresent.domain.auth.model.dto.LoginDTO;
import com.macaron.corpresent.domain.auth.model.dto.RegisterDTO;
import com.macaron.corpresent.domain.auth.model.vo.LoginVO;
import com.macaron.corpresent.domain.auth.service.EmailIdentifyService;
import com.macaron.corpresent.domain.auth.service.LoginService;
import com.macaron.corpresent.domain.auth.service.PasswordIdentifyService;
import com.macaron.corpresent.domain.auth.service.RegisterService;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 10:47
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@Tag(name = "认证校验（无需登录/无需权限）")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final EmailIdentifyService emailIdentifyService;

    private final PasswordIdentifyService passwordIdentifyService;

    private final LoginService loginService;

    private final RegisterService registerService;

    private final UserService userService;

    @Operation(summary = "获取邮箱验证码")
    @PostMapping("/email/identify")
    public SystemJsonResponse<?> emailIdentify(@Valid @RequestBody EmailIdentifyDTO emailIdentifyDTO) {
        emailIdentifyService.sendIdentifyingCode(emailIdentifyDTO.getIdentifyType(), emailIdentifyDTO.getEmail());
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public SystemJsonResponse<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        LoginVO loginVO = loginService.login(loginDTO);
        return SystemJsonResponse.SYSTEM_SUCCESS(loginVO);
    }

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public SystemJsonResponse<?> register(@Valid @RequestBody RegisterDTO registerDTO) {
        registerService.register(registerDTO);
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    @Operation(summary = "用户找回密码")
    @PostMapping("/find/password")
    public SystemJsonResponse<?> findPassword(@Valid @RequestBody FindPasswordDTO findPasswordDTO) {
        String email = findPasswordDTO.getEmail();
        // 判断邮箱是否存在
        User user = userService.checkAndGetUserByEmail(email);
        // 验证邮箱验证码
        emailIdentifyService.validateEmailCode(EmailIdentifyType.FIND_PASSWORD, email, findPasswordDTO.getCode());
        // 更新密码
        String dbPassword = passwordIdentifyService.passwordEncrypt(findPasswordDTO.getPassword());
        userService.updatePasswordUser(user.getId(), dbPassword);
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

}
