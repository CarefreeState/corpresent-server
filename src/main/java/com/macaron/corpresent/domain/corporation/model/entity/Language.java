package com.macaron.corpresent.domain.corporation.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName language
 */
@TableName(value ="language")
@Data
public class Language implements Serializable {
    private Long id;

    private String languageName;

    private String languageCode;

    private Integer version;

    private Boolean isDeleted;

    private Long createTime;

    private Long updateTime;

    private static final long serialVersionUID = 1L;
}