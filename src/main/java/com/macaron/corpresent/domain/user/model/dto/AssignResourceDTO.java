package com.macaron.corpresent.domain.user.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 16:28
 */
@Data
@Schema(description = "分配资源数据")
public class AssignResourceDTO{

    @Schema(description = "资源 id 列表")
    private List<Long> resourceIds;

}
