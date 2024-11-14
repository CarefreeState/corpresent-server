package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.common.util.convert.ObjectUtil;
import com.macaron.corpresent.domain.user.model.dao.mapper.UserRoleRelationMapper;
import com.macaron.corpresent.domain.user.model.dto.AssignRoleDTO;
import com.macaron.corpresent.domain.user.model.entity.UserRoleRelation;
import com.macaron.corpresent.domain.user.service.UserRoleRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* @author 马拉圈
* @description 针对表【user_role_relation(用户-角色关联表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleRelationMapper, UserRoleRelation>
    implements UserRoleRelationService{

    @Override
    @Transactional
    public void createUserRoleRelation(Long userId, AssignRoleDTO assignRoleDTO) {
        // 删除原有关系
        removeUserRoleRelation(userId);
        List<UserRoleRelation> userRoleRelationList = ObjectUtil.distinctNonNullStream(assignRoleDTO.getRoleIds()).map(roleId -> {
            UserRoleRelation userRoleRelation = new UserRoleRelation();
            userRoleRelation.setUserId(userId);
            userRoleRelation.setRoleId(roleId);
            return userRoleRelation;
        }).toList();
        // 若不为空集合则批量插入
        if(!CollectionUtils.isEmpty(userRoleRelationList)) {
            this.saveBatch(userRoleRelationList);
        }
    }

    @Override
    public void removeUserRoleRelation(Long userId) {
        this.lambdaUpdate()
                .eq(UserRoleRelation::getUserId, userId)
                .remove();
    }

    @Override
    public List<Long> queryRoleIdsByUserId(Long userId) {
        return this.lambdaQuery()
                .eq(UserRoleRelation::getUserId, userId)
                .list()
                .stream()
                .map(UserRoleRelation::getRoleId)
                .toList();
    }
}




