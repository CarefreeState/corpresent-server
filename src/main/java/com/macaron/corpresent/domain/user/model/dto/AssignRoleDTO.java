package com.macaron.corpresent.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 16:29
 */
@Data
@Schema(description = "分配角色数据")
public class AssignRoleDTO {

    @Schema(description = "角色 id 列表")
    private List<Long> roleIds;

}
