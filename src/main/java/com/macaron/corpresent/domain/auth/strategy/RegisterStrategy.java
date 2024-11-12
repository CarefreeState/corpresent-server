package com.macaron.corpresent.domain.auth.strategy;

import com.macaron.corpresent.domain.auth.model.dto.RegisterDTO;
import com.macaron.corpresent.domain.user.model.entity.User;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 23:20
 */
public interface RegisterStrategy {

    String BASE_NAME = "RegisterStrategy";

    String getUsername(RegisterDTO registerDTO);

    User register(RegisterDTO registerDTO, boolean check);

}
