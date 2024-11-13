package com.macaron.corpresent.domain.auth.model.dto;

import com.macaron.corpresent.domain.auth.enums.LoginType;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "登录数据")
public class LoginDTO {

    @NotBlank(message = "登录方式不能为空")
    @Schema(description = "登录方式") // OpenAPI 搭配 Validation 的注解，区分可选和必须
    private LoginType loginType;

    /**
     * 邮箱登录数据
     */
    @Valid
    @Schema(description = "邮箱数据")
    private EmailDTO emailParams;

    /**
     * 密码登录数据
     */
    @Valid
    @Schema(description = "密码数据")
    private PasswordDTO passwordParams;

}
