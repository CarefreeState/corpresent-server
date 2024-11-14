package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.common.base.BasePageQuery;
import com.macaron.corpresent.common.base.BasePageResult;
import com.macaron.corpresent.common.constants.EntitySortConstants;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.user.constants.UserConstants;
import com.macaron.corpresent.domain.user.model.converter.UserConverter;
import com.macaron.corpresent.domain.user.model.dao.mapper.UserMapper;
import com.macaron.corpresent.domain.user.model.dto.AssignRoleDTO;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.dto.UserQueryDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.model.vo.UserQueryVO;
import com.macaron.corpresent.domain.user.model.vo.UserVO;
import com.macaron.corpresent.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
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
                new GlobalServiceException(GlobalServiceStatusCode.USER_USERNAME_PASSWORD_ERROR));
    }

    @Override
    public User checkAndGetUserByEmail(String email) {
        return getUserByEmail(email).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.EMAIL_USER_ACCOUNT_NOT_EXIST));
    }

    @Override
    @Transactional
    public User createUser(UserDTO userDTO) {
        User user = UserConverter.INSTANCE.userDTOToUser(userDTO);
        user.setSort(this.count() * EntitySortConstants.BASE_SORT_NUMBER);
        this.save(user);
        // 设置默认角色
        AssignRoleDTO assignRoleDTO = AssignRoleDTO.builder()
                .roleIds(List.of(UserConstants.DEFAULT_ROLE))
                .build();
        userRoleRelationService.createUserRoleRelation(user.getId(), assignRoleDTO);
        return user;
    }

    @Override
    public void blockUser(Long userId, Boolean isBlocked) {
        this.lambdaUpdate()
                .eq(User::getId, userId)
                .set(User::getIsBlocked, isBlocked)
                .update();
    }

    @Override
    public void renameUser(Long userId, String nickname) {
        this.lambdaUpdate()
                .eq(User::getId, userId)
                .set(User::getNickname, nickname)
                .update();
    }

    @Override
    public void updatePasswordUser(Long userId, String password) {
        this.lambdaUpdate()
                .eq(User::getId, userId)
                .set(User::getPassword, password)
                .update();
    }

    @Override
    public UserVO queryUser(Long userId) {
        return UserConverter.INSTANCE.userToUserVO(checkAndGetUserById(userId));
    }

    @Override
    public UserQueryVO queryUser(UserQueryDTO userQueryDTO) {
        // 解析分页参数获取 page
        IPage<User> page = null;
        String name = null;
        // 获取条件分页查询参数
        if(Objects.nonNull(userQueryDTO)) {
            page = userQueryDTO.toMpPage();
            name = userQueryDTO.getName();
        } else {
            page = new BasePageQuery().toMpPage();
        }
        // 分页
        IPage<User> userIPage = this.lambdaQuery()
                .like(StringUtils.hasText(name), User::getUsername, name)
                .like(StringUtils.hasText(name), User::getNickname, name)
                .page(page);
        // 封装
        BasePageResult<User> userBasePageResult = BasePageResult.of(userIPage);
        // 转化并返回
        return UserConverter.INSTANCE.userBasePageResultToUserQueryVO(userBasePageResult);
    }

}




