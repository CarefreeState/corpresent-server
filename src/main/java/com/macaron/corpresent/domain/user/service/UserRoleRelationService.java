package com.macaron.corpresent.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macaron.corpresent.domain.user.model.dto.AssignRoleDTO;
import com.macaron.corpresent.domain.user.model.entity.UserRoleRelation;

import java.util.List;

/**
* @author 马拉圈
* @description 针对表【user_role_relation(用户-角色关联表)】的数据库操作Service
* @createDate 2024-11-12 13:22:47
*/
public interface UserRoleRelationService extends IService<UserRoleRelation> {

    void createUserRoleRelation(Long userId, AssignRoleDTO assignRoleDTO);
    void removeUserRoleRelation(Long userId);

    List<Long> queryRoleIdsByUserId(Long userId);

}
