package com.macaron.corpresent.domain.user.model.dto;

import com.macaron.corpresent.common.base.BasePageQuery;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 17:06
 */
@Data
@Schema(description = "用户查询数据")
public class UserQueryDTO extends BasePageQuery {

    @Schema(description = "用户名/昵称")
    private String name;

}
