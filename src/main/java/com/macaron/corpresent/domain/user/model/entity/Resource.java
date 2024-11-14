package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macaron.corpresent.common.base.BaseIncrIDEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName resource
 */
@TableName(value ="resource")
@Data
public class Resource extends BaseIncrIDEntity implements Serializable {

    private Long categoryId;

    private String path;

    private String name;

    private String description;

    private Boolean isBlocked;

    private static final long serialVersionUID = 1L;
}