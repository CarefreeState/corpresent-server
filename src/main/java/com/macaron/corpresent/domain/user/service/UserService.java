package com.macaron.corpresent.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
* @author 马拉圈
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2024-11-12 13:22:47
*/
public interface UserService extends IService<User> {

    Optional<User> getUserById(Long userId);

    Optional<User> getUserByUsername(String username);
    Optional<User> getUserByEmail(String email);

    User createUser(UserDTO userDTO);

    User checkAndGetUserByUsername(String username);
    User checkAndGetUserByEmail(String email);

    List<Resource> getResourceListByUserId(Long userId);

}
