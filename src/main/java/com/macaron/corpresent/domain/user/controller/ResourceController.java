package com.macaron.corpresent.domain.user.controller;

import com.macaron.corpresent.common.SystemJsonResponse;
import com.macaron.corpresent.domain.user.model.dto.ResourceDTO;
import com.macaron.corpresent.domain.user.model.dto.ResourceQueryDTO;
import com.macaron.corpresent.domain.user.model.vo.ResourceDetailVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceQueryVO;
import com.macaron.corpresent.domain.user.service.ResourceCategoryService;
import com.macaron.corpresent.domain.user.service.ResourceService;
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
 * Time: 11:50
 */
@RequiredArgsConstructor
@RestController
@Slf4j
@Validated
@Tag(name = "资源")
@RequestMapping("/api/v1/resource")
public class ResourceController {

    private final ResourceService resourceService;

    private final ResourceCategoryService resourceCategoryService;

    @PostMapping("/query")
    @Operation(summary = "分页条件查询资源列表")
    public SystemJsonResponse<ResourceQueryVO> queryResource(@Valid @RequestBody(required = false) ResourceQueryDTO resourceQueryDTO) {
        ResourceQueryVO resourceQueryVO = resourceService.queryResource(resourceQueryDTO);
        return SystemJsonResponse.SYSTEM_SUCCESS(resourceQueryVO);
    }

    @PostMapping("/add")
    @Operation(summary = "添加一个资源")
    public SystemJsonResponse<Long> createResource(@Valid @RequestBody ResourceDTO resourceDTO) {
        // 判断资源分类是否存在
        resourceCategoryService.checkAndGetResourceCategory(resourceDTO.getCategoryId());
        // 添加
        Long resourceId = resourceService.createResource(resourceDTO).getId();
        // 刷新本地对资源的缓存
        return SystemJsonResponse.SYSTEM_SUCCESS(resourceId);
    }

    @DeleteMapping("/remove/{resourceId}")
    @Operation(summary = "删除一个资源")
    public SystemJsonResponse<?> removeResource(@PathVariable("resourceId") @NotNull(message = "资源 id 不能为空") Long resourceId) {
        // 判断资源是否存在
        resourceService.checkAndGetResource(resourceId);
        // 删除
        resourceService.removeResource(resourceId);
        // 刷新本地对资源的缓存
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    @PutMapping("/update/{resourceId}")
    @Operation(summary = "更新一个资源")
    public SystemJsonResponse<?> updateResource(@PathVariable("resourceId") @NotNull(message = "资源 id 不能为空") Long resourceId,
                                                @Valid @RequestBody ResourceDTO resourceDTO) {
        // 判断资源是否存在
        resourceService.checkAndGetResource(resourceId);
        // 更新
        resourceService.updateResource(resourceId, resourceDTO);
        // 刷新本地对资源的缓存
        return SystemJsonResponse.SYSTEM_SUCCESS();
    }

    @GetMapping("/detail/{resourceId}")
    @Operation(summary = "查询资源详情")
    public SystemJsonResponse<ResourceDetailVO> queryResourceDetail(@PathVariable("resourceId") @NotNull(message = "资源 id 不能为空") Long resourceId) {
        ResourceDetailVO resourceDetailVO = resourceService.queryResourceDetail(resourceId);
        return SystemJsonResponse.SYSTEM_SUCCESS(resourceDetailVO);
    }

}
