package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.common.constants.EntitySortConstants;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.user.constants.UserConstants;
import com.macaron.corpresent.domain.user.model.converter.UserConverter;
import com.macaron.corpresent.domain.user.model.dao.mapper.UserMapper;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.model.vo.UserVO;
import com.macaron.corpresent.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
* @author 马拉圈
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    private final UserMapper userMapper;

    private final UserRoleRelationServiceImpl userRoleRelationService;

    @Override
    public Optional<User> getUserById(Long userId) {
        return this.lambdaQuery()
                .eq(User::getId, userId)
                .oneOpt();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return this.lambdaQuery()
                .eq(User::getUsername, username)
                .oneOpt();
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return this.lambdaQuery()
                .eq(User::getEmail, email)
                .oneOpt();
    }

    @Override
    public List<Resource> getResourceListByUserId(Long userId) {
        return userMapper.getResourceList(userId);
    }

    @Override
    public User checkAndGetUserById(Long userId) {
        return getUserById(userId).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST));
    }

    @Override
    public User checkAndGetUserByUsername(String username) {
        return getUserByUsername(username).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST));

    }

    @Override
    public User checkAndGetUserByEmail(String email) {
        return getUserByEmail(email).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.USER_ACCOUNT_NOT_EXIST));
    }

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = UserConverter.INSTANCE.userDTOToUser(userDTO);
        user.setSort(this.count() * EntitySortConstants.BASE_SORT_NUMBER);
        this.save(user);
        // 设置默认角色
        userRoleRelationService.createUserRoleRelation(user.getId(), List.of(UserConstants.DEFAULT_ROLE));
        return user;
    }

    @Override
    public UserVO getUserVOById(Long userId) {
        return UserConverter.INSTANCE.userToUserVO(checkAndGetUserById(userId));
    }

}




