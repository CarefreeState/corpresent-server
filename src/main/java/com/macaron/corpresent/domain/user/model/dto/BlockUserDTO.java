package com.macaron.corpresent.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 17:23
 */
@Data
@Schema(description = "禁用用户数据")
public class BlockUserDTO {

    @NotNull(message = "是否禁用不能为空")
    @Schema(description = "是否禁用")
    private Boolean isBlocked;
}
