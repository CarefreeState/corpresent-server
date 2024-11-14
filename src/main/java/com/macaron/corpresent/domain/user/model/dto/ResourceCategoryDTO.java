package com.macaron.corpresent.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 13:09
 */
@Data
@Schema(description = "资源分类数据")
public class ResourceCategoryDTO {

    @NotBlank(message = "资源分类名称不能为空")
    @Schema(description = "资源分类名称")
    private String name;

}
