package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.domain.user.model.entity.Role;
import com.macaron.corpresent.domain.user.service.RoleService;
import com.macaron.corpresent.domain.user.model.dao.mapper.RoleMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【role(角色表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

}



