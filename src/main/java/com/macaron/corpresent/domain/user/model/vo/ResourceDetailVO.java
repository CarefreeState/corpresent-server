package com.macaron.corpresent.domain.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 14:28
 */
@Data
@Schema(description = "资源详情信息")
public class ResourceDetailVO {

    @Schema(description = "资源 id")
    private Long id;

    @Schema(description = "资源分类信息")
    private ResourceCategoryVO resourceCategoryVO;

    @Schema(description = "资源路径")
    private String pattern;

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "资源描述")
    private String description;

    @Schema(description = "是否禁用")
    private Boolean isBlocked;

}
