package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.user.model.converter.ResourceCategoryConverter;
import com.macaron.corpresent.domain.user.model.dao.mapper.ResourceCategoryMapper;
import com.macaron.corpresent.domain.user.model.dto.ResourceCategoryDTO;
import com.macaron.corpresent.domain.user.model.entity.ResourceCategory;
import com.macaron.corpresent.domain.user.model.vo.ResourceCategoryDetailVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceCategoryVO;
import com.macaron.corpresent.domain.user.service.ResourceCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
* @author 马拉圈
* @description 针对表【resource_category(资源分类表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceCategoryServiceImpl extends ServiceImpl<ResourceCategoryMapper, ResourceCategory>
    implements ResourceCategoryService {

    private final ResourceCategoryMapper resourceCategoryMapper;

    @Override
    public Optional<ResourceCategory> getResourceCategory(Long categoryId) {
        return this.lambdaQuery()
                .eq(ResourceCategory::getId, categoryId)
                .oneOpt();
    }

    @Override
    public ResourceCategory checkAndGetResourceCategory(Long categoryId) {
        return getResourceCategory(categoryId).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.RESOURCE_CATEGORY_NOT_EXISTS));
    }

    @Override
    public ResourceCategory createResourceCategory(ResourceCategoryDTO resourceCategoryDTO) {
        ResourceCategory resourceCategory = ResourceCategoryConverter.INSTANCE.resourceCategoryDTOToResourceCategory(resourceCategoryDTO);
        this.save(resourceCategory);
        return resourceCategory;
    }

    @Override
    public void removeResourceCategory(Long categoryId) {
        this.lambdaUpdate()
                .eq(ResourceCategory::getId, categoryId)
                .remove();
    }

    @Override
    public void updateResourceCategory(Long categoryId, ResourceCategoryDTO resourceCategoryDTO) {
        this.lambdaUpdate()
                .eq(ResourceCategory::getId, categoryId)
                .update(ResourceCategoryConverter.INSTANCE.resourceCategoryDTOToResourceCategory(resourceCategoryDTO));
    }

    @Override
    public List<ResourceCategoryVO> queryResourceCategory() {
        return ResourceCategoryConverter.INSTANCE.resourceCategoryListToResourceCategoryVOList(this.list());
    }

    @Override
    public List<ResourceCategoryDetailVO> queryResourceCategoryDetail() {
        return resourceCategoryMapper.queryResourceCategoryDetail();
    }
}




