package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName role_resource_relation
 */
@TableName(value ="role_resource_relation")
@Data
public class RoleResourceRelation implements Serializable {
    private Long id;

    private Long roleId;

    private Long resourceId;

    private Integer version;

    private Boolean isDeleted;

    private Long createTime;

    private Long updateTime;

    private Boolean isBlocked;

    private Double sort;

    private static final long serialVersionUID = 1L;
}