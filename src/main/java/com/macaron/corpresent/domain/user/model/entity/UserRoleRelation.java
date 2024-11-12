package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macaron.corpresent.common.base.BaseIncrIDEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user_role_relation
 */
@TableName(value ="user_role_relation")
@Data
public class UserRoleRelation extends BaseIncrIDEntity implements Serializable {

    private Long userId;

    private Long roleId;

    private static final long serialVersionUID = 1L;
}