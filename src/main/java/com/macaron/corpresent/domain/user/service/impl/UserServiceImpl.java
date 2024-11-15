package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.common.base.BasePageQuery;
import com.macaron.corpresent.common.base.BasePageResult;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.user.constants.UserConstants;
import com.macaron.corpresent.domain.user.model.converter.UserConverter;
import com.macaron.corpresent.domain.user.model.dao.mapper.UserMapper;
import com.macaron.corpresent.domain.user.model.dto.SortUserDTO;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.dto.UserQueryDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.model.entity.UserRoleRelation;
import com.macaron.corpresent.domain.user.model.vo.UserQueryVO;
import com.macaron.corpresent.domain.user.model.vo.UserVO;
import com.macaron.corpresent.domain.user.service.UserService;
import com.macaron.corpresent.domain.user.util.UserThreadPool;
import com.macaron.corpresent.redis.lock.RedisLock;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.macaron.corpresent.common.constants.EntitySortConstants.BASE_SORT_NUMBER;
import static com.macaron.corpresent.common.constants.EntitySortConstants.MIN_SORT_PRECISION;

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

    private final RedisLock redisLock;

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
        return redisLock.tryLockGetSomething(UserConstants.SORT_USER_LOCK, () -> {
            User user = UserConverter.INSTANCE.userDTOToUser(userDTO);
            user.setSort((this.count() + 1) * BASE_SORT_NUMBER);
            this.save(user);
            // 设置默认角色
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUserId(user.getId());
            userRoleRelation.setRoleId(UserConstants.DEFAULT_ROLE);
            userRoleRelationService.save(userRoleRelation);
            return user;
        }, () -> null);
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
    @Transactional
    public void sortUser() {
        redisLock.tryLockDoSomething(UserConstants.SORT_USER_LOCK, () -> {
            log.info("重置全部用户的排序字段");
            List<User> userList = this.lambdaQuery()
                    .orderBy(Boolean.TRUE, Boolean.TRUE, User::getSort)
                    .list();
            int size = userList.size();
            List<User> updateUserList = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                User updateUser = new User();
                updateUser.setId(userList.get(i).getId());
                updateUser.setSort((i + 1) * BASE_SORT_NUMBER);
                updateUserList.add(updateUser);
            }
            this.updateBatchById(updateUserList);
        }, () -> {});
    }

    @Override
    @Transactional
    public void sortAndCheckUser(User moveUser, User besideUser) {
        Optional.ofNullable(besideUser).map(User::getSort).ifPresent(besideUserSort -> {
            Double moveUserSort = moveUser.getSort();
            log.info("排序后用户：{} sort {}", moveUser.getId(), moveUserSort);
            this.lambdaUpdate()
                    .eq(User::getId, moveUser.getId())
                    .set(User::getSort, moveUser.getSort())
                    .update();
            // 判断间隔
            if(Math.abs(moveUserSort - besideUserSort) <= MIN_SORT_PRECISION) {
                log.info("排序字段达到最小间隔，发起异步线程，重置全部元素的排序字段");
                UserThreadPool.submit(this::sortUser);
            }
        });
    }

    @Override
    @Transactional
    public void sortUser(SortUserDTO sortUserDTO) {
        redisLock.tryLockDoSomething(UserConstants.SORT_USER_LOCK, () -> {
            User moveUser = checkAndGetUserById(sortUserDTO.getMoveUserId());
            log.info("待排序用户：{} sort {}", moveUser.getId(), moveUser.getSort());
            User besideUser = Optional.ofNullable(sortUserDTO.getPreUserId()).map(preUserId -> {
                // 哪怕两个都有，也优先“排序到 preUserId 的后面”
                User preUser = checkAndGetUserById(preUserId);
                Double preUserSort = preUser.getSort();
                userMapper.getNextUser(preUserId).ifPresentOrElse(nextUser -> {
                    // 有下一个用户
                    Double nextUserSort = nextUser.getSort();
                    log.info("用户排序到 {} {} 与 {} {} 之间", preUserId, preUserSort, nextUser.getId(), nextUserSort);
                    moveUser.setSort((preUserSort + nextUserSort) / 2);
                }, () -> {
                    // 最后一个
                    log.info("用户排序到最后一位");
                    moveUser.setSort(preUserSort + BASE_SORT_NUMBER);
                });
                return preUser;
            }).orElseGet(() -> {
                return Optional.ofNullable(sortUserDTO.getNextUserId()).map(nextUserId -> {
                            User nextUser = checkAndGetUserById(nextUserId);
                            Double nextUserSort = nextUser.getSort();
                            userMapper.getPreUser(nextUserId).ifPresentOrElse(preUser -> {
                                // 有上一个用户
                                Double preUserSort = preUser.getSort();
                                log.info("用户排序到 {} {} 与 {} {} 之间", preUser.getId(), preUserSort, nextUserId, nextUserSort);
                                moveUser.setSort((preUserSort + nextUserSort) / 2);
                            }, () -> {
                                // 第一个
                                log.info("用户排序到第一位");
                                moveUser.setSort(nextUserSort / 2);
                            });
                            return nextUser;
                        })
                        // preUserId 和 nextUserId 都为 null（统一认为那一页只有一个这一个用户）
                        .orElse(null);
            });
            // 更新
            sortAndCheckUser(moveUser, besideUser);
        }, () -> {});
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
                .or()
                .like(StringUtils.hasText(name), User::getNickname, name)
                .orderBy(Boolean.TRUE, Boolean.TRUE, User::getSort)
                .page(page);
        // 封装
        BasePageResult<User> userBasePageResult = BasePageResult.of(userIPage);
        // 转化并返回
        return UserConverter.INSTANCE.userBasePageResultToUserQueryVO(userBasePageResult);
    }

}




