package com.macaron.corpresent.domain.auth.strategy;

import com.macaron.corpresent.domain.auth.model.dto.LoginDTO;
import com.macaron.corpresent.domain.user.model.entity.User;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 16:24
 */
public interface LoginStrategy {

    String BASE_NAME = "LoginStrategy";

    User login(LoginDTO loginDTO);

}
