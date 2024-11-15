package com.macaron.corpresent.domain.corporation.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName corporation_info
 */
@TableName(value ="corporation_info")
@Data
public class CorporationInfo implements Serializable {
    private Long id;

    private String logo;

    private String email;

    private String phoneNumber;

    private String serviceHotline;

    private String wechatServiceAccount;

    private String wechatQrCode;

    private Integer version;

    private Boolean isDeleted;

    private Long createTime;

    private Long updateTime;

    private static final long serialVersionUID = 1L;
}