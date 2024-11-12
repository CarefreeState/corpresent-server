package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.service.ResourceService;
import com.macaron.corpresent.domain.user.model.dao.mapper.ResourceMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【resource(资源表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService{

}




