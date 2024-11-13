package com.macaron.corpresent.domain.auth.enums;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 11:28
 */
@Getter
@AllArgsConstructor
public enum LoginType {

    EMAIL("email"),
    PASSWORD("password"),

    ;

    @JsonValue
    private final String name;

}
