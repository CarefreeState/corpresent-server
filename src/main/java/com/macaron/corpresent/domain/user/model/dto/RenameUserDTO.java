package com.macaron.corpresent.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 17:29
 */
@Data
@Schema(description = "用户改名数据")
public class RenameUserDTO {

    @Schema(description = "角色昵称")
    private String nickname;

}
