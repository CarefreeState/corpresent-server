package com.macaron.corpresent.domain.user.model.converter;

import com.macaron.corpresent.common.base.BasePageResult;
import com.macaron.corpresent.domain.user.model.dto.ResourceDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.vo.ResourceDetailVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceQueryVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 13:27
 */
@Mapper
public interface ResourceConverter {

    ResourceConverter INSTANCE = Mappers.getMapper(ResourceConverter.class);

    Resource resourceDTOToResource(ResourceDTO resourceDTO);

    ResourceQueryVO resourceBasePageResultToResourceQueryVO(BasePageResult<Resource> resourceBasePageResult);

    ResourceDetailVO resourceCategoryToResourceDetailVO(Resource resource);

    List<ResourceVO> resourceListToResourceVOList(List<Resource> resourceList);
}
