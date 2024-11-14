package com.macaron.corpresent.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macaron.corpresent.domain.user.model.dto.ResourceCategoryDTO;
import com.macaron.corpresent.domain.user.model.entity.ResourceCategory;
import com.macaron.corpresent.domain.user.model.vo.ResourceCategoryDetailVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceCategoryVO;

import java.util.List;
import java.util.Optional;

/**
* @author 马拉圈
* @description 针对表【resource_category(资源分类表)】的数据库操作Service
* @createDate 2024-11-12 13:22:47
*/
public interface ResourceCategoryService extends IService<ResourceCategory> {

    Optional<ResourceCategory> getResourceCategory(Long categoryId);

    ResourceCategory checkAndGetResourceCategory(Long categoryId);

    ResourceCategory createResourceCategory(ResourceCategoryDTO resourceCategoryDTO);
    void removeResourceCategory(Long categoryId);
    void updateResourceCategory(Long categoryId, ResourceCategoryDTO resourceCategoryDTO);

    List<ResourceCategoryVO> queryResourceCategory();
    List<ResourceCategoryDetailVO> queryResourceCategoryDetail();

}
