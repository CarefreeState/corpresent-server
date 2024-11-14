package com.macaron.corpresent.domain.user.model.dto;

import com.macaron.corpresent.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 14:17
 */
@Data
@Schema(description = "资源查询数据")
public class ResourceQueryDTO extends BasePageQuery {

    @Schema(description = "资源分类 id")
    private Long categoryId;

    @Schema(description = "资源路径")
    private String path;

    @Schema(description = "资源名称")
    private String name;

}
