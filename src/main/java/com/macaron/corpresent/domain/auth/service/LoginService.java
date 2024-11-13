package com.macaron.corpresent.domain.auth.service;

import com.macaron.corpresent.domain.auth.model.dto.LoginDTO;
import com.macaron.corpresent.domain.auth.model.vo.LoginVO;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 17:53
 */
public interface LoginService {

    LoginVO login(LoginDTO loginDTO);

}
