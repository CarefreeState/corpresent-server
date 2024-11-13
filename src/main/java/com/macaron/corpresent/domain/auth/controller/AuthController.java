package com.macaron.corpresent.domain.auth.controller;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.domain.auth.model.dto.EmailIdentifyDTO;
import com.macaron.corpresent.domain.auth.model.dto.LoginDTO;
import com.macaron.corpresent.domain.auth.model.dto.RegisterDTO;
import com.macaron.corpresent.domain.auth.model.vo.LoginVO;
import com.macaron.corpresent.domain.auth.service.EmailIdentifyService;
import com.macaron.corpresent.domain.auth.service.LoginService;
import com.macaron.corpresent.domain.auth.service.RegisterService;
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
@Tag(name = "认证校验")
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final EmailIdentifyService emailIdentifyService;

    private final LoginService loginService;

    private final RegisterService registerService;

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

}
