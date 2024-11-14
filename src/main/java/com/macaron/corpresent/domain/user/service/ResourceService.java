package com.macaron.corpresent.domain.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.macaron.corpresent.domain.user.model.dto.ResourceDTO;
import com.macaron.corpresent.domain.user.model.dto.ResourceQueryDTO;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.vo.ResourceDetailVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceQueryVO;
import com.macaron.corpresent.domain.user.model.vo.ResourceVO;

import java.util.List;
import java.util.Optional;

/**
* @author 马拉圈
* @description 针对表【resource(资源表)】的数据库操作Service
* @createDate 2024-11-12 13:22:47
*/
public interface ResourceService extends IService<Resource> {

    Optional<Resource> getResource(Long resourceId);

    Resource checkAndGetResource(Long resourceId);

    Resource createResource(ResourceDTO resourceDTO);
    void removeResource(Long resourceId);
    void updateResource(Long resourceId, ResourceDTO resourceDTO);

    ResourceQueryVO queryResource(ResourceQueryDTO resourceQueryDTO);
    List<ResourceVO> queryResource(List<Long> resourceIds);
    ResourceDetailVO queryResourceDetail(Long resourceId);

}
