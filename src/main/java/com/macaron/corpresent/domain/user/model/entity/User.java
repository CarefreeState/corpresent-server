package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    private Long id;

    private String username;

    private String nickname;

    private String password;

    private String email;

    private String phoneNumber;

    private String icon;

    private Integer version;

    private Boolean isDeleted;

    private Long createTime;

    private Long updateTime;

    private Boolean isBlocked;

    private Double sort;

    private static final long serialVersionUID = 1L;
}