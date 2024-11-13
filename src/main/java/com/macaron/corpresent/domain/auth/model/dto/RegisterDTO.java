package com.macaron.corpresent.domain.auth.model.dto;

import com.macaron.corpresent.domain.auth.enums.RegisterType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 密码登录才会用到这个 DTO 注册，其余都是直接新建
 *
 * @author BanTanger 半糖
 * @date 2024/1/22 23:06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "注册数据")
public class RegisterDTO {

    @NotNull(message = "注册方式不能为空")
    @Schema(description = "注册方式")
    private RegisterType registerType;

    /**
     * 邮箱注册数据
     */
    @Valid
    @Schema(description = "邮箱数据")
    private EmailDTO emailParams;

    /**
     * 密码注册数据
     */
    @Valid
    @Schema(description = "密码数据")
    private PasswordDTO passwordParams;


}
