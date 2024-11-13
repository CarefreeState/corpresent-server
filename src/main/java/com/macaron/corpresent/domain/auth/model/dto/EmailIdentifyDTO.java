package com.macaron.corpresent.domain.auth.model.dto;

import com.macaron.corpresent.domain.auth.enums.EmailIdentifyType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 10:55
 */
@Data
@Schema(description = "邮箱验证数据")
public class EmailIdentifyDTO {

    @NotBlank(message = "校验类型不能为空")
    @Schema(description = "校验类型")
    private EmailIdentifyType identifyType;

    @Email(message = "邮箱格式非法")
    @NotBlank(message = "邮箱不能为空")
    @Schema(description = "邮箱验证码")
    private String email;

}
