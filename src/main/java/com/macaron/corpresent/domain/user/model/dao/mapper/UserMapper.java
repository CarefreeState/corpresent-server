package com.macaron.corpresent.domain.user.model.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
* @author 马拉圈
* @description 针对表【user(用户表)】的数据库操作Mapper
* @createDate 2024-11-12 13:22:47
* @Entity com.macaron.corpresent.domain.user.model.entity.User
*/
public interface UserMapper extends BaseMapper<User> {

    /**
     * 获取用户所有可访问资源
     */
    List<Resource> getResourceList(@Param("userId") Long userId);

    Optional<User> getPreUser(@Param("userId") Long userId);

    Optional<User> getNextUser(@Param("userId") Long userId);

}




