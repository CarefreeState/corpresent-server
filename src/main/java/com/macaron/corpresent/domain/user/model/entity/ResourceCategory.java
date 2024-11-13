package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macaron.corpresent.common.base.BaseIncrIDEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName resource_category
 */
@TableName(value ="resource_category")
@Data
public class ResourceCategory extends BaseIncrIDEntity implements Serializable {

    private String name;

    private static final long serialVersionUID = 1L;
}