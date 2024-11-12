package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
import com.macaron.corpresent.domain.user.model.dao.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




