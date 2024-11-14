package com.macaron.corpresent.domain.user.model.converter;

import com.macaron.corpresent.common.base.BasePageResult;
import com.macaron.corpresent.domain.user.model.dto.RoleDTO;
import com.macaron.corpresent.domain.user.model.entity.Role;
import com.macaron.corpresent.domain.user.model.vo.RoleQueryVO;
import com.macaron.corpresent.domain.user.model.vo.RoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 13:34
 */
@Mapper
public interface RoleConverter {

    RoleConverter INSTANCE = Mappers.getMapper(RoleConverter.class);

    Role roleDTOToRole(RoleDTO roleDTO);

    RoleQueryVO roleBasePageResultToRoleQueryVO(BasePageResult<Role> roleBasePageResult);

    List<RoleVO> roleListToRoleVOList(List<Role> roleList);
}
