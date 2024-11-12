package com.macaron.corpresent.email.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author BanTanger 半糖
 * @date 2024/1/23 9:27
 */
@Getter
@AllArgsConstructor
public enum EmailTemplate {

    EMAIL_IDENTIFY("邮箱验证", "identifying-code-model.html"),


    ;

    /**
     * 本次邮件拟定标题
     */
    private final String title;
    /**
     * 本次邮件格式模板
     */
    private final String template;

}
