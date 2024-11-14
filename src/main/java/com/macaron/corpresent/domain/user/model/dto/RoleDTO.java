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
 * Time: 13:30
 */
@Data
@Schema(description = "角色数据")
public class RoleDTO {

    @NotBlank(message = "角色名称不能为空")
    @Schema(description = "角色名称")
    private String name;

    @NotBlank(message = "角色描述不能为空")
    @Schema(description = "角色描述")
    private String description;

    @NotNull(message = "是否禁用不能为空")
    @Schema(description = "是否禁用")
    private Boolean isBlocked;

}
