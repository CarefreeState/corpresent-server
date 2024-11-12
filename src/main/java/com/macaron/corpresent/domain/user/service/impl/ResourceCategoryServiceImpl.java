package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.domain.user.model.entity.ResourceCategory;
import com.macaron.corpresent.domain.user.service.ResourceCategoryService;
import com.macaron.corpresent.domain.user.model.dao.mapper.ResourceCategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author 马拉圈
* @description 针对表【resource_category(资源分类表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
public class ResourceCategoryServiceImpl extends ServiceImpl<ResourceCategoryMapper, ResourceCategory>
    implements ResourceCategoryService {

}




