package com.macaron.corpresent.domain.user.model.dto;

import com.macaron.corpresent.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 14:58
 */
@Data
@Schema(description = "角色查询数据")
public class RoleQueryDTO extends BasePageQuery {

    @Schema(description = "角色名称")
    private String name;

}
