package com.macaron.corpresent.domain.user.model.vo;

import com.macaron.corpresent.common.base.BasePageResult;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 14:56
 */
@Data
@Schema(description = "角色查询结果")
public class RoleQueryVO extends BasePageResult<RoleVO> {
}
