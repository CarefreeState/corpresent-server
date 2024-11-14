package com.macaron.corpresent.domain.auth.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 11:26
 */
@Getter
@AllArgsConstructor
public enum EmailIdentifyType {

    LOGIN("login", "登录验证", GlobalServiceStatusCode.EMAIL_LOGIN_IDENTIFY_CODE_ERROR),
    REGISTER("register", "注册验证", GlobalServiceStatusCode.EMAIL_REGISTER_IDENTIFY_CODE_ERROR),
    FIND_PASSWORD("findPassword", "找回密码", GlobalServiceStatusCode.EMAIL_REGISTER_IDENTIFY_CODE_ERROR),

    ;

    @JsonValue
    private final String name;

    private final String description;

    private final GlobalServiceStatusCode errorCode;

}
