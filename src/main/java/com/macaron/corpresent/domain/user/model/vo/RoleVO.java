package com.macaron.corpresent.domain.user.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 14:57
 */
@Data
@Schema(description = "角色信息")
public class RoleVO {

    @Schema(description = "角色 id")
    private Long id;

    @Schema(description = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String description;

    @Schema(description = "是否禁用")
    private Boolean isBlocked;

}
