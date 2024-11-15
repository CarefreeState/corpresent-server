package com.macaron.corpresent.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-15
 * Time: 13:01
 */
@Data
@Schema(description = "排序用户数据")
public class SortUserDTO {

    @Schema(description = "上一个用户的 id")
    private Long preUserId;

    @Schema(description = "下一个用户的 id")
    private Long nextUserId;

    @Schema(description = "移动的用户的 id")
    @NotNull(message = "移动的用户的 id 不能为 null")
    private Long moveUserId;

}
