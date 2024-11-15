package com.macaron.corpresent.domain.corporation.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName corporation_photo
 */
@TableName(value ="corporation_photo")
@Data
public class CorporationPhoto implements Serializable {
    private Long id;

    private Long corporationId;

    private String url;

    private Integer type;

    private Integer version;

    private Boolean isDeleted;

    private Long createTime;

    private Long updateTime;

    private static final long serialVersionUID = 1L;
}