package com.macaron.corpresent.domain.auth.service;

import com.macaron.corpresent.domain.user.model.entity.User;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 0:22
 */
public interface PasswordIdentifyService {

    String passwordEncrypt(String password);

    User validatePassword(String username, String password);

}