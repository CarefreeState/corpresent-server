package com.macaron.corpresent.domain.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 15:11
 */
@Data
@Schema(description = "资源分类详细信息")
public class ResourceCategoryDetailVO {

    @Schema(description = "资源分类 id")
    private Long id;

    @Schema(description = "资源分类名称")
    private String name;

    @Schema(description = "资源列表")
    private List<ResourceVO> resourceVOList;

}
