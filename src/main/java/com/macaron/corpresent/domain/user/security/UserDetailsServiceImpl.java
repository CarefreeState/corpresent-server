package com.macaron.corpresent.domain.user.security;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 10:57
 */
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.checkAndGetUserByUsername(username);
        if(Boolean.TRUE.equals(user.getIsBlocked())) {
            throw new GlobalServiceException(GlobalServiceStatusCode.USER_ACCOUNT_BLOCKED);
        }
        Long userId = user.getId();
        List<Resource> resourceList = userService.getResourceListByUserId(userId);
        return new UserResourceDetails(user, resourceList);
    }
}
