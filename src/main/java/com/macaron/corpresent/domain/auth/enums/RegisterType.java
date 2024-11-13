package com.macaron.corpresent.domain.auth.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 11:13
 */
@Getter
@AllArgsConstructor
public enum RegisterType {

    EMAIL("email"),
    PASSWORD("password"),
    PASSWORD_EMAIL("passwordEmail"),

    ;

    @JsonValue
    private final String name;

}
