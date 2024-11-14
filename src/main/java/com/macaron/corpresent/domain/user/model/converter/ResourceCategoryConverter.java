package com.macaron.corpresent.domain.user.model.converter;

import com.macaron.corpresent.domain.user.model.dto.ResourceCategoryDTO;
import com.macaron.corpresent.domain.user.model.entity.ResourceCategory;
import com.macaron.corpresent.domain.user.model.vo.ResourceCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 13:26
 */
@Mapper
public interface ResourceCategoryConverter {

    ResourceCategoryConverter INSTANCE = Mappers.getMapper(ResourceCategoryConverter.class);

    ResourceCategory resourceCategoryDTOToResourceCategory(ResourceCategoryDTO resourceCategoryDTO);

    List<ResourceCategoryVO> resourceCategoryListToResourceCategoryVOList(List<ResourceCategory> resourceCategoryList);

    ResourceCategoryVO resourceCategoryToResourceCategoryVO(ResourceCategory resourceCategory);

}
