package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.common.util.convert.ObjectUtil;
import com.macaron.corpresent.domain.user.annotation.ResourceClear;
import com.macaron.corpresent.domain.user.model.dao.mapper.RoleResourceRelationMapper;
import com.macaron.corpresent.domain.user.model.dto.AssignResourceDTO;
import com.macaron.corpresent.domain.user.model.entity.RoleResourceRelation;
import com.macaron.corpresent.domain.user.service.RoleResourceRelationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* @author 马拉圈
* @description 针对表【role_resource_relation(角色-资源关联表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class RoleResourceRelationServiceImpl extends ServiceImpl<RoleResourceRelationMapper, RoleResourceRelation>
    implements RoleResourceRelationService{


    @Override
    @Transactional
    @ResourceClear
    public void createRoleResourceRelation(Long roleId, AssignResourceDTO assignResourceDTO) {
        // 删除原有关系
        removeRoleResourceRelation(roleId);
        List<RoleResourceRelation> roleResourceRelationList = ObjectUtil.distinctNonNullStream(assignResourceDTO.getResourceIds()).map(resourceId -> {
            RoleResourceRelation roleResourceRelation = new RoleResourceRelation();
            roleResourceRelation.setRoleId(roleId);
            roleResourceRelation.setResourceId(resourceId);
            return roleResourceRelation;
        }).toList();
        // 若不为空集合则批量插入
        if(!CollectionUtils.isEmpty(roleResourceRelationList)) {
            this.saveBatch(roleResourceRelationList);
        }
    }

    @Override
    @ResourceClear
    public void removeRoleResourceRelation(Long roleId) {
        this.lambdaUpdate()
                .eq(RoleResourceRelation::getRoleId, roleId)
                .remove();
    }

    @Override
    public List<Long> queryResourceIdsByRoleId(Long roleId) {
        return this.lambdaQuery()
                .eq(RoleResourceRelation::getRoleId, roleId)
                .list()
                .stream()
                .map(RoleResourceRelation::getResourceId)
                .toList();
    }
}




