package com.macaron.corpresent.domain.user.controller;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.domain.user.model.dto.ResourceDTO;
import com.macaron.corpresent.domain.user.model.dto.ResourceQueryDTO;
import com.macaron.corpresent.domain.user.model.dto.RoleDTO;
import com.macaron.corpresent.domain.user.model.dto.RoleQueryDTO;
import com.macaron.corpresent.domain.user.model.vo.ResourceQueryVO;
import com.macaron.corpresent.domain.user.model.vo.RoleQueryVO;
import com.macaron.corpresent.domain.user.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 16:01
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@Tag(name = "角色")
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/query")
    @Operation(summary = "分页条件查询角色列表")
    public SystemJsonResponse<RoleQueryVO> queryRole(@Valid @RequestBody(required = false) RoleQueryDTO roleQueryDTO) {
        RoleQueryVO roleQueryVO = roleService.queryRole(roleQueryDTO);
        return SystemJsonResponse.SYSTEM_SUCCESS(roleQueryVO);
    }

    @PostMapping("/add")
    @Operation(summary = "添加一个角色")
    public SystemJsonResponse<Long> createRole(@Valid @RequestBody RoleDTO roleDTO) {
        // 添加
        Long roleId = roleService.createRole(roleDTO).getId();
        return SystemJsonResponse.SYSTEM_SUCCESS(roleId);
    }

    @DeleteMapping("/remove/{roleId}")
    @Operation(summary = "删除一个角色")
    public SystemJsonResponse<?> removeRole(@PathVariable("roleId") @NotNull(message = "角色 id 不能为空") Long roleId) {
        // 判断角色是否存在
        roleService.checkAndGetRole(roleId);
        // 删除
        roleService.removeRole(roleId);
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    @PutMapping("/update/{roleId}")
    @Operation(summary = "更新一个角色")
    public SystemJsonResponse<?> updateRole(@PathVariable("roleId") @NotNull(message = "角色 id 不能为空") Long roleId,
                                                @Valid @RequestBody RoleDTO roleDTO) {
        // 判断角色是否存在
        roleService.checkAndGetRole(roleId);
        // 更新
        roleService.updateRole(roleId, roleDTO);
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

}
