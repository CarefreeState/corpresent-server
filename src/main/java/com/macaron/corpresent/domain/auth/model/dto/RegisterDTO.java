package com.macaron.corpresent.domain.auth.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

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
public class RegisterDTO {

    @NotBlank(message = "注册方式不能为空")
    private String registerType;

    /**
     * 邮箱注册数据
     */
    @Valid
    private EmailDTO emailParams;

    /**
     * 密码注册数据
     */
    @Valid
    private PasswordDTO passwordParams;


}
