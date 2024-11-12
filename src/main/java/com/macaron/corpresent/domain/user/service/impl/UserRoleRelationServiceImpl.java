package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.domain.user.model.dao.mapper.UserRoleRelationMapper;
import com.macaron.corpresent.domain.user.model.entity.UserRoleRelation;
import com.macaron.corpresent.domain.user.service.UserRoleRelationService;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【user_role_relation(用户-角色关联表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
public class UserRoleRelationServiceImpl extends ServiceImpl<UserRoleRelationMapper, UserRoleRelation>
    implements UserRoleRelationService{

}




