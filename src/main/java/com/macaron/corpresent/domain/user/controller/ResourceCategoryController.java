package com.macaron.corpresent.domain.user.controller;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.domain.user.model.dto.ResourceCategoryDTO;
import com.macaron.corpresent.domain.user.model.vo.ResourceCategoryDetailVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceCategoryVO;
import com.macaron.corpresent.domain.user.service.ResourceCategoryService;
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
 * Time: 13:08
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@Tag(name = "权限管理（资源分类）")
@RequestMapping("/api/v1/security/category/resource")
public class ResourceCategoryController {

    private final ResourceCategoryService resourceCategoryService;

    @GetMapping("/list")
    @Operation(summary = "查询所有的资源分类")
    public SystemJsonResponse<List<ResourceCategoryVO>> queryResourceCategory() {
        List<ResourceCategoryVO> resourceCategoryVOList = resourceCategoryService.queryResourceCategory();
        return SystemJsonResponse.SYSTEM_SUCCESS(resourceCategoryVOList);
    }

    @GetMapping("/detail/list")
    @Operation(summary = "详细查询所有的资源分类")
    public SystemJsonResponse<List<ResourceCategoryDetailVO>> queryResourceCategoryDetail() {
        List<ResourceCategoryDetailVO> resourceCategoryDetailVOList = resourceCategoryService.queryResourceCategoryDetail();
        return SystemJsonResponse.SYSTEM_SUCCESS(resourceCategoryDetailVOList);
    }

    @PostMapping("/add")
    @Operation(summary = "添加一个资源分类")
    public SystemJsonResponse<Long> createResourceCategory(@Valid @RequestBody ResourceCategoryDTO resourceCategoryDTO) {
        // 添加
        Long categoryId = resourceCategoryService.createResourceCategory(resourceCategoryDTO).getId();
        return SystemJsonResponse.SYSTEM_SUCCESS(categoryId);
    }

    @DeleteMapping("/remove/{categoryId}")
    @Operation(summary = "删除一个资源分类")
    public SystemJsonResponse<?> removeResourceCategory(@PathVariable("categoryId") @NotNull(message = "资源分类 id 不能为空") Long categoryId) {
        // 判断资源分类是否存在
        resourceCategoryService.checkAndGetResourceCategory(categoryId);
        // 删除
        resourceCategoryService.removeResourceCategory(categoryId);
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    @PutMapping("/update/{categoryId}")
    @Operation(summary = "更新一个资源分类")
    public SystemJsonResponse<?> updateResourceCategory(@PathVariable("categoryId") @NotNull(message = "资源分类 id 不能为空") Long categoryId,
                                                        @Valid @RequestBody ResourceCategoryDTO resourceCategoryDTO) {
        // 判断资源分类是否存在
        resourceCategoryService.checkAndGetResourceCategory(categoryId);
        // 更新
        resourceCategoryService.updateResourceCategory(categoryId, resourceCategoryDTO);
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

}
