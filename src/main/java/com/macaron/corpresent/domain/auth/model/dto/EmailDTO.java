package com.macaron.corpresent.domain.auth.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "邮箱数据")
public class EmailDTO {

    @Email(message = "邮箱格式非法")
    @NotBlank(message = "邮箱不能为空")
    @Schema(description = "邮箱")
    private String email;

    @NotBlank(message = "邮箱验证码不能为空")
    @Schema(description = "邮箱验证码")
    private String code;

}
