package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.common.base.BasePageQuery;
import com.macaron.corpresent.common.base.BasePageResult;
import com.macaron.corpresent.common.util.convert.ObjectUtil;
import com.macaron.corpresent.domain.user.model.converter.RoleConverter;
import com.macaron.corpresent.domain.user.model.dao.mapper.RoleMapper;
import com.macaron.corpresent.domain.user.model.dto.RoleDTO;
import com.macaron.corpresent.domain.user.model.dto.RoleQueryDTO;
import com.macaron.corpresent.domain.user.model.entity.Role;
import com.macaron.corpresent.domain.user.model.vo.RoleQueryVO;
import com.macaron.corpresent.domain.user.model.vo.RoleVO;
import com.macaron.corpresent.domain.user.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
* @author 马拉圈
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Override
    public Role createRole(RoleDTO roleDTO) {
        Role role = RoleConverter.INSTANCE.roleDTOToRole(roleDTO);
        this.save(role);
        return role;
    }

    @Override
    public void removeRole(Long roleId) {
        this.lambdaUpdate()
                .eq(Role::getId, roleId)
                .remove();
    }

    @Override
    public void updateRole(Long roleId, RoleDTO roleDTO) {
        this.lambdaUpdate()
                .eq(Role::getId, roleId)
                .update(RoleConverter.INSTANCE.roleDTOToRole(roleDTO));
    }

    @Override
    public RoleQueryVO queryRole(RoleQueryDTO roleQueryDTO) {
        // 解析分页参数获取 page
        IPage<Role> page = null;
        String name = null;
        // 获取条件分页查询参数
        if(Objects.nonNull(roleQueryDTO)) {
            page = roleQueryDTO.toMpPage();
            name = roleQueryDTO.getName();
        } else {
            page = new BasePageQuery().toMpPage();
        }
        // 分页
        IPage<Role> resourceIPage = this.lambdaQuery()
                .like(StringUtils.hasText(name), Role::getName, name)
                .page(page);
        // 封装
        BasePageResult<Role> roleBasePageResult = BasePageResult.of(resourceIPage);
        // 转化并返回
        return RoleConverter.INSTANCE.roleBasePageResultToRoleQueryVO(roleBasePageResult);
    }

    @Override
    public List<RoleVO> queryRole(List<Long> roleIds) {
        roleIds = ObjectUtil.distinctNonNullStream(roleIds).toList();
        if(CollectionUtils.isEmpty(roleIds)) {
            return new ArrayList<>();
        }
        List<Role> roleList = this.lambdaQuery().in(Role::getId, roleIds).list();
        return RoleConverter.INSTANCE.roleListToRoleVOList(roleList);
    }
}




