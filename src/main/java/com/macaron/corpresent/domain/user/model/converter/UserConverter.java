package com.macaron.corpresent.domain.user.model.converter;

import com.macaron.corpresent.common.base.BasePageResult;
import com.macaron.corpresent.domain.user.model.dto.UserDTO;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.model.vo.UserQueryVO;
import com.macaron.corpresent.domain.user.model.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-12
 * Time: 18:45
 */
@Mapper
public interface UserConverter {

    UserConverter INSTANCE = Mappers.getMapper(UserConverter.class);

    User userDTOToUser(UserDTO userDTO);

    UserVO userToUserVO(User user);

    UserQueryVO userBasePageResultToUserQueryVO(BasePageResult<User> userBasePageResult);

}
