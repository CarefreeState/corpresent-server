package com.macaron.corpresent.domain.user.aspect;

import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.user.annotation.ResourceClear;
import com.macaron.corpresent.domain.user.constants.ResourceConstants;
import com.macaron.corpresent.redis.cache.RedisCache;
import com.macaron.corpresent.redis.lock.RedisLock;
import com.macaron.corpresent.redis.lock.strategy.WriteLockStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-15
 * Time: 18:47
 */
@Aspect
@RequiredArgsConstructor
@Slf4j
@Component
public class ResourceCacheAspect {

    private final RedisLock redisLock;

    private final RedisCache redisCache;

    private final WriteLockStrategy writeLockStrategy;

    @Pointcut("execution(public * com.macaron.corpresent.domain.user.service..*(..)) && @annotation(com.macaron.corpresent.domain.user.annotation.ResourceClear))")
    public void pointcut() {};

    @Around("pointcut()")
    public Object clearResourceCache(ProceedingJoinPoint joinPoint) {
        return redisLock.tryLockGetSomething(ResourceConstants.RESOURCE_READ_WRITE_LOCK, () -> {
            Object result = null;
            try {
                result = joinPoint.proceed();
            } catch (Throwable e) {
                throw new GlobalServiceException(e.getMessage());
            }
            String prefix = ResourceConstants.USER_RESOURCE_SET;
            if(joinPoint.getSignature() instanceof MethodSignature methodSignature) {
                ResourceClear annotation = methodSignature.getMethod().getAnnotation(ResourceClear.class);
                if(annotation.isSpecified()) {
                    Object[] args = joinPoint.getArgs();
                    if(args.length >= 1 && args[0] instanceof Long userId) {
                        prefix += userId;
                    }
                }
            }
            // 执行完写操作删除用户拥有的资源缓存
            Set<String> keysByPrefix = redisCache.getKeysByPrefix(prefix);
            redisCache.deleteObjects(keysByPrefix);
            return result;
        }, () -> null, writeLockStrategy);
    }

}
