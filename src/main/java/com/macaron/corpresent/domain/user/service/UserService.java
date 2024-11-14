package com.macaron.corpresent.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.model.vo.UserVO;

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
    List<Resource> getResourceListByUserId(Long userId);

    User checkAndGetUserById(Long userId);
    User checkAndGetUserByUsername(String username);

    User createUser(UserDTO userDTO);

    UserVO getUserVOById(Long userId);

}
