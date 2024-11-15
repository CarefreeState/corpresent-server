package com.macaron.corpresent.domain.corporation.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName corporation_info_language_relation
 */
@TableName(value ="corporation_info_language_relation")
@Data
public class CorporationInfoLanguageRelation implements Serializable {
    private Long id;

    private Long corporationId;

    private Long languageId;

    private String name;

    private String introduction;

    private String address;

    private Integer version;

    private Boolean isDeleted;

    private Long createTime;

    private Long updateTime;

    private static final long serialVersionUID = 1L;
}