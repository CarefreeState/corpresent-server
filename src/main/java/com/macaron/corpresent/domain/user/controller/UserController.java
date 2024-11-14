package com.macaron.corpresent.domain.user.controller;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.domain.user.model.dto.AssignRoleDTO;
import com.macaron.corpresent.domain.user.model.dto.BlockUserDTO;
import com.macaron.corpresent.domain.user.model.dto.RenameUserDTO;
import com.macaron.corpresent.domain.user.model.dto.UserQueryDTO;
import com.macaron.corpresent.domain.user.model.vo.RoleVO;
import com.macaron.corpresent.domain.user.model.vo.UserQueryVO;
import com.macaron.corpresent.domain.user.model.vo.UserVO;
import com.macaron.corpresent.domain.user.service.RoleService;
import com.macaron.corpresent.domain.user.service.UserRoleRelationService;
import com.macaron.corpresent.domain.user.service.UserService;
import com.macaron.corpresent.security.context.BaseContext;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    private final RoleService roleService;

    private final UserRoleRelationService userRoleRelationService;

    @GetMapping("/own/info")
    @Operation(summary = "读取当前用户信息")
    public SystemJsonResponse<UserVO> getUserInfo() {
        Long userId = BaseContext.getCurrentUser().getUserId();
        UserVO userVO = userService.queryUser(userId);
        return SystemJsonResponse.SYSTEM_SUCCESS(userVO);
    }

    @GetMapping("/info/{userId}")
    @Operation(summary = "读取指定用户信息")
    public SystemJsonResponse<UserVO> getUserInfoByUserId(@PathVariable("userId") @NotNull(message = "用户 id 不能为空") Long userId) {
        UserVO userInfoVO = userService.queryUser(userId);
        return SystemJsonResponse.SYSTEM_SUCCESS(userInfoVO);
    }

    @PostMapping("/query")
    @Operation(summary = "分页条件查询用户列表")
    public SystemJsonResponse<UserQueryVO> queryRole(@Valid @RequestBody(required = false) UserQueryDTO userQueryDTO) {
        UserQueryVO userQueryVO = userService.queryUser(userQueryDTO);
        return SystemJsonResponse.SYSTEM_SUCCESS(userQueryVO);
    }

    @GetMapping("/role/list/{userId}")
    @Operation(summary = "查询用户关联的角色")
    public SystemJsonResponse<List<RoleVO>> queryRolesByUserId(@PathVariable("userId") @NotNull(message = "用户 id 不能为空") Long userId) {
        userService.checkAndGetUserById(userId);
        List<Long> roleIds = userRoleRelationService.queryRoleIdsByUserId(userId);
        List<RoleVO> roleVOList = roleService.queryRole(roleIds);
        return SystemJsonResponse.SYSTEM_SUCCESS(roleVOList);
    }

    @PutMapping("/role/assign/{userId}")
    @Operation(summary = "为用户分配角色")
    public SystemJsonResponse<?> assignRolesForUser(@PathVariable("userId") @NotNull(message = "用户 id 不能为空") Long userId,
                                                    @Valid @RequestBody AssignRoleDTO assignRoleDTO) {
        userService.checkAndGetUserById(userId);
        // todo: 判断当前用户是否可以访问关联的所有的资源
        userRoleRelationService.createUserRoleRelation(userId, assignRoleDTO);
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    @PutMapping("/block/{userId}")
    @Operation(summary = "禁用用户")
    public SystemJsonResponse<?> blockUser(@PathVariable("userId") @NotNull(message = "用户 id 不能为空") Long userId,
                                           @Valid @RequestBody BlockUserDTO blockUserDTO) {
        userService.checkAndGetUserById(userId);
        userService.blockUser(userId, blockUserDTO.getIsBlocked());
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    @PutMapping("/rename")
    @Operation(summary = "用户重命名")
    public SystemJsonResponse<?> blockUser(@Valid @RequestBody RenameUserDTO renameUserDTO) {
        Long userId = BaseContext.getCurrentUser().getUserId();
        userService.renameUser(userId, renameUserDTO.getNickname());
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    // todo: 拖拽排序

}
