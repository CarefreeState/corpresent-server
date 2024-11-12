package com.macaron.corpresent.domain.auth.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 16:36
 */
@Data
public class LoginDTO {

    @NotBlank(message = "登录方式不能为空")
    private String loginType;

    /**
     * 邮箱登录数据
     */
    @Valid
    private EmailDTO emailParams;

    /**
     * 密码登录数据
     */
    @Valid
    private PasswordDTO passwordParams;

}
