package com.macaron.corpresent.domain.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 14:03
 */
@Data
@Schema(description = "资源分类信息")
public class ResourceCategoryVO {

    @Schema(description = "资源分类 id")
    private Long id;

    @Schema(description = "资源分类名称")
    private String name;

}
