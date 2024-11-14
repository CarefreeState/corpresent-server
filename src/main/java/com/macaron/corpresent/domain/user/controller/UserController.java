package com.macaron.corpresent.domain.user.controller;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.model.vo.UserVO;
import com.macaron.corpresent.domain.user.service.UserService;
import com.macaron.corpresent.security.context.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 11:50
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@Tag(name = "用户")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/own/info")
    @Operation(summary = "读取当前用户信息")
    public SystemJsonResponse<UserVO> getUserInfo() {
        Long userId = BaseContext.getCurrentUser().getUserId();
        UserVO userVO = userService.getUserVOById(userId);
        return SystemJsonResponse.SYSTEM_SUCCESS(userVO);
    }

    @GetMapping("/info/{userId}")
    @Operation(summary = "读取指定用户信息")
    public SystemJsonResponse<UserVO> getUserInfoByUserId(@PathVariable("userId") @NotNull(message = "用户 id 不能为空") Long userId) {
        UserVO userInfoVO = userService.getUserVOById(userId);
        return SystemJsonResponse.SYSTEM_SUCCESS(userInfoVO);
    }

}
