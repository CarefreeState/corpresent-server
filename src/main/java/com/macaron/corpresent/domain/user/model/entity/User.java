package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macaron.corpresent.common.base.BaseIncrIDSortEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User extends BaseIncrIDSortEntity implements Serializable {

    private String username;

    private String nickname;

    private String password;

    private String email;

    private String phoneNumber;

    private String icon;

    private Boolean isBlocked;

    private static final long serialVersionUID = 1L;
}