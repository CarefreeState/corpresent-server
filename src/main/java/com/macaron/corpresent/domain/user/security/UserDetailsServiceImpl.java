package com.macaron.corpresent.domain.user.security;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.user.constants.ResourceConstants;
import com.macaron.corpresent.domain.user.model.entity.Resource;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.domain.user.service.UserService;
import com.macaron.corpresent.redis.cache.RedisCache;
import com.macaron.corpresent.redis.cache.RedisSetCache;
import com.macaron.corpresent.redis.lock.RedisLock;
import com.macaron.corpresent.redis.lock.strategy.ReadLockStrategy;
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

    private final RedisCache redisCache;

    private final RedisSetCache redisSetCache;

    private final RedisLock redisLock;

    private final ReadLockStrategy readLockStrategy;

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.checkAndGetUserByUsername(username);
        // 禁用的用户无法通过认证，更没必要鉴权
        if(Boolean.TRUE.equals(user.getIsBlocked())) {
            throw new GlobalServiceException(GlobalServiceStatusCode.USER_ACCOUNT_BLOCKED);
        }
        Set<String> patterns = redisLock.tryLockGetSomething(ResourceConstants.RESOURCE_READ_WRITE_LOCK, () -> {
            String redisKey = ResourceConstants.USER_RESOURCE_SET + user.getId();
            return redisSetCache.getSet(redisKey, String.class).orElseGet(() -> {
                Set<String> resourceSet = userService.getResourceListByUserId(user.getId())
                        .stream()
                        .map(Resource::getPattern)
                        .collect(Collectors.toSet());
                redisSetCache.init(redisKey, resourceSet, ResourceConstants.USER_RESOURCE_SET_TIMEOUT, ResourceConstants.USER_RESOURCE_SET_TIMEUNIT);
                return resourceSet;
            });
        }, () -> null, readLockStrategy);
        log.info("用户 {} 能够访问的资源：{}", username, patterns);
        return new UserResourceDetails(user, patterns);
    }
}
