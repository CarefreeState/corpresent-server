package com.macaron.corpresent.domain.user.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.macaron.corpresent.common.base.BaseIncrIDEntity;
import lombok.Data;

import java.io.Serializable;

/**
 * @TableName user_login_log
 */
@TableName(value ="user_login_log")
@Data
public class UserLoginLog extends BaseIncrIDEntity implements Serializable {

    private Long userId;

    private String ip;

    private String address;

    private String userAgent;

    private static final long serialVersionUID = 1L;
}