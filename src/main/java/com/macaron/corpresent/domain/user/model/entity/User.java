package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macaron.corpresent.common.base.BaseIncrIDEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User extends BaseIncrIDEntity implements Serializable {

    private String username;

    private String nickname;

    private String password;

    private String email;

    private String phoneNumber;

    private String icon;

    private static final long serialVersionUID = 1L;
}