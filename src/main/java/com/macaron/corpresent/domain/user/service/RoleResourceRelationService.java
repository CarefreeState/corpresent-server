package com.macaron.corpresent.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macaron.corpresent.domain.user.model.dto.AssignResourceDTO;
import com.macaron.corpresent.domain.user.model.entity.RoleResourceRelation;

import java.util.List;

/**
* @author 马拉圈
* @description 针对表【role_resource_relation(角色-资源关联表)】的数据库操作Service
* @createDate 2024-11-12 13:22:47
*/
public interface RoleResourceRelationService extends IService<RoleResourceRelation> {

    void createRoleResourceRelation(Long roleId, AssignResourceDTO assignResourceDTO);
    void removeRoleResourceRelation(Long roleId);

    List<Long> queryResourceIdsByRoleId(Long roleId);

}
