package com.macaron.corpresent.domain.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.macaron.corpresent.common.base.BasePageQuery;
import com.macaron.corpresent.common.base.BasePageResult;
import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.common.util.convert.ObjectUtil;
import com.macaron.corpresent.domain.user.annotation.ResourceClear;
import com.macaron.corpresent.domain.user.model.converter.ResourceConverter;
import com.macaron.corpresent.domain.user.model.dao.mapper.ResourceMapper;
import com.macaron.corpresent.domain.user.model.dto.ResourceDTO;
import com.macaron.corpresent.domain.user.model.dto.ResourceQueryDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.ResourceCategory;
import com.macaron.corpresent.domain.user.model.vo.ResourceDetailVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceQueryVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceVO;
import com.macaron.corpresent.domain.user.service.ResourceCategoryService;
import com.macaron.corpresent.domain.user.service.ResourceService;
import com.macaron.corpresent.redis.cache.RedisCache;
import com.macaron.corpresent.redis.lock.RedisLock;
import com.macaron.corpresent.redis.lock.strategy.WriteLockStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
* @author 马拉圈
* @description 针对表【resource(资源表)】的数据库操作Service实现
* @createDate 2024-11-12 13:22:47
*/
@Service
@Slf4j
@RequiredArgsConstructor
public class ResourceServiceImpl extends ServiceImpl<ResourceMapper, Resource>
    implements ResourceService{

    private final RedisLock redisLock;

    private final RedisCache redisCache;

    private final WriteLockStrategy writeLockStrategy;

    private final ResourceCategoryService resourceCategoryService;

    @Override
    public Optional<Resource> getResource(Long resourceId) {
        return this.lambdaQuery()
                .eq(Resource::getId, resourceId)
                .oneOpt();
    }

    @Override
    public Resource checkAndGetResource(Long resourceId) {
        return getResource(resourceId).orElseThrow(() ->
                new GlobalServiceException(GlobalServiceStatusCode.RESOURCE_NOT_EXISTS));
    }

    @Override
    public Resource createResource(ResourceDTO resourceDTO) {
        Resource resource = ResourceConverter.INSTANCE.resourceDTOToResource(resourceDTO);
        this.save(resource);
        return resource;
    }

    @Override
    @ResourceClear
    public void removeResource(Long resourceId) {
        this.lambdaUpdate()
                .eq(Resource::getId, resourceId)
                .remove();
    }

    @Override
    @ResourceClear
    public void updateResource(Long resourceId, ResourceDTO resourceDTO) {
        this.lambdaUpdate()
                .eq(Resource::getId, resourceId)
                .update(ResourceConverter.INSTANCE.resourceDTOToResource(resourceDTO));
    }

    @Override
    public ResourceQueryVO queryResource(ResourceQueryDTO resourceQueryDTO) {
        // 解析分页参数获取 page
        IPage<Resource> page = null;
        Long categoryId = null;
        String pattern = null;
        String name = null;
        // 获取条件分页查询参数
        if(Objects.nonNull(resourceQueryDTO)) {
            page = resourceQueryDTO.toMpPage();
            categoryId = resourceQueryDTO.getCategoryId();
            pattern = resourceQueryDTO.getPattern();
            name = resourceQueryDTO.getName();
        } else {
            page = new BasePageQuery().toMpPage();
        }
        // 分页
        IPage<Resource> resourceIPage = this.lambdaQuery()
                .eq(Objects.nonNull(categoryId), Resource::getCategoryId, categoryId)
                .like(StringUtils.hasText(pattern), Resource::getPattern, pattern)
                .like(StringUtils.hasText(name), Resource::getName, name)
                .page(page);
        // 封装
        BasePageResult<Resource> resourceBasePageResult = BasePageResult.of(resourceIPage);
        // 转化并返回
        return ResourceConverter.INSTANCE.resourceBasePageResultToResourceQueryVO(resourceBasePageResult);
    }

    @Override
    public List<ResourceVO> queryResource(List<Long> resourceIds) {
        resourceIds = ObjectUtil.distinctNonNullStream(resourceIds).toList();
        if(CollectionUtils.isEmpty(resourceIds)) {
            return new ArrayList<>();
        }
        List<Resource> resourceList = this.lambdaQuery().in(Resource::getId, resourceIds).list();
        return ResourceConverter.INSTANCE.resourceListToResourceVOList(resourceList);
    }

    @Override
    public ResourceDetailVO queryResourceDetail(Long resourceId) {
        Resource resource = checkAndGetResource(resourceId);
        ResourceCategory resourceCategory = resourceCategoryService.getResourceCategory(resource.getCategoryId()).orElse(null);
        return ResourceConverter.INSTANCE.resourceCategoryToResourceDetailVO(resource, resourceCategory);
    }
}




