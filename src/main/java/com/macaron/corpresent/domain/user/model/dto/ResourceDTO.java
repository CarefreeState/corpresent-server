package com.macaron.corpresent.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 13:01
 */
@Data
@Schema(description = "资源数据")
public class ResourceDTO {

    @NotNull(message = "资源分类 id 不能为空")
    @Schema(description = "资源分类 id")
    private Long categoryId;

    @NotBlank(message = "资源路径不能为空")
    @Schema(description = "资源路径")
    private String path;

    @NotBlank(message = "资源名称不能为空")
    @Schema(description = "资源名称")
    private String name;

    @NotBlank(message = "资源描述不能为空")
    @Schema(description = "资源描述")
    private String description;

    @NotNull(message = "是否禁用不能为空")
    @Schema(description = "是否禁用")
    private Boolean isBlocked;
}
