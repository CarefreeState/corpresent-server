package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macaron.corpresent.common.base.BaseIncrIDEntity;
import com.macaron.corpresent.common.base.BaseIncrIDSortEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName role_resource_relation
 */
@TableName(value ="role_resource_relation")
@Data
public class RoleResourceRelation extends BaseIncrIDEntity implements Serializable {

    private Long roleId;

    private Long resourceId;

    private static final long serialVersionUID = 1L;
}