package com.macaron.corpresent.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macaron.corpresent.domain.user.model.dto.SortUserDTO;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.dto.UserQueryDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.model.vo.UserQueryVO;
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
    User checkAndGetUserByEmail(String email);

    User createUser(UserDTO userDTO);
    void blockUser(Long userId, Boolean isBlocked);
    void renameUser(Long userId, String nickname);
    void updatePasswordUser(Long userId, String password);

    void sortUser();
    void sortAndCheckUser(User moveUser, User besideUser);
    void sortUser(SortUserDTO sortUserDTO);

    UserVO queryUser(Long userId);
    UserQueryVO queryUser(UserQueryDTO userQueryDTO);

}
