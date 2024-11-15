package com.macaron.corpresent.domain.user.security;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 10:57
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.checkAndGetUserByUsername(username);
        if(Boolean.TRUE.equals(user.getIsBlocked())) {
            throw new GlobalServiceException(GlobalServiceStatusCode.USER_ACCOUNT_BLOCKED);
        }
        Set<String> patterns = userService.getResourceListByUserId(user.getId())
                .stream()
                .map(Resource::getPattern)
                .collect(Collectors.toSet());
        log.info("用户 {} 能够访问的资源：{}", username, patterns);
        return new UserResourceDetails(user, patterns);
    }
}
