package com.macaron.corpresent.domain.auth.service;

import com.macaron.corpresent.domain.auth.model.dto.RegisterDTO;
import com.macaron.corpresent.domain.user.model.entity.User;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 18:24
 */
public interface RegisterService {

    User register(RegisterDTO registerBody);

}
