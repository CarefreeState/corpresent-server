package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.domain.user.model.entity.UserLoginLog;
import com.macaron.corpresent.domain.user.service.UserLoginLogService;
import com.macaron.corpresent.domain.user.model.dao.mapper.UserLoginLogMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【user_login_log(用户登录记录)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
public class UserLoginLogServiceImpl extends ServiceImpl<UserLoginLogMapper, UserLoginLog>
    implements UserLoginLogService{

}




