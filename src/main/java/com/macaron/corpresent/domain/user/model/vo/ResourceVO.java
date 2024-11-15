package com.macaron.corpresent.domain.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 14:09
 */
@Data
@Schema(description = "资源信息")
public class ResourceVO {

    @Schema(description = "资源 id")
    private Long id;

    @Schema(description = "资源分类 id")
    private Long categoryId;

    @Schema(description = "资源路径")
    private String pattern;

    @Schema(description = "资源名称")
    private String name;

    @Schema(description = "资源描述")
    private String description;

    @Schema(description = "创建时间")
    protected Long createTime;

    @Schema(description = "是否禁用")
    private Boolean isBlocked;

}
