package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macaron.corpresent.common.base.BaseIncrIDEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName role
 */
@TableName(value ="role")
@Data
public class Role extends BaseIncrIDEntity implements Serializable {

    private String name;

    private String description;

    private Integer userCount;

    private static final long serialVersionUID = 1L;
}