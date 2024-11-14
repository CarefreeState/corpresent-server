package com.macaron.corpresent.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macaron.corpresent.domain.user.model.dto.RoleDTO;
import com.macaron.corpresent.domain.user.model.dto.RoleQueryDTO;
import com.macaron.corpresent.domain.user.model.entity.Role;
import com.macaron.corpresent.domain.user.model.vo.ResourceVO;
import com.macaron.corpresent.domain.user.model.vo.RoleQueryVO;
import com.macaron.corpresent.domain.user.model.vo.RoleVO;

import java.util.List;

/**
* @author 马拉圈
* @description 针对表【role(角色表)】的数据库操作Service
* @createDate 2024-11-12 13:22:47
*/
public interface RoleService extends IService<Role> {

    Role createRole(RoleDTO roleDTO);
    void removeRole(Long roleId);
    void updateRole(Long roleId, RoleDTO roleDTO);

    RoleQueryVO queryRole(RoleQueryDTO roleQueryDTO);
    List<RoleVO> queryRole(List<Long> roleIds);

}
