package com.macaron.corpresent.domain.auth.service.impl;

import com.macaron.corpresent.common.enums.GlobalServiceStatusCode;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.domain.auth.constants.AuthConstants;
import com.macaron.corpresent.domain.auth.service.ValidateService;
import com.macaron.corpresent.redis.cache.RedisCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

import static com.macaron.corpresent.domain.auth.constants.AuthConstants.*;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 0:46
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final RedisCache redisCache;

    @Override
    public void validate(String key, Supplier<Boolean> isValid, GlobalServiceStatusCode statusCode) {
        String failKey = VALIDATE_KEY + key;
        // 获取失败次数
        Integer failCount = redisCache.<Integer>getCacheObject(failKey).orElseGet(() -> {
            Integer count = 0;
            redisCache.setCacheObject(failKey, count, VALIDATE_BLOCKED_TIMEOUT, VALIDATE_BLOCKED_TIMEUNIT);
            return count;
        });
        // 锁定时间禁止
        if (failCount.compareTo(AuthConstants.VALIDATE_MAX_RETRY_COUNT) >= 0) {
            String message = String.format("已连续 %d 次%s，请过 %d 分钟后再尝试",
                    failCount, statusCode.getMessage(), VALIDATE_BLOCKED_TIMEUNIT.toMinutes(VALIDATE_BLOCKED_TIMEOUT));
            throw new GlobalServiceException(message, GlobalServiceStatusCode.PARAM_FAILED_VALIDATE);
        }
        if(Boolean.FALSE.equals(isValid.get())) {
            long count = redisCache.incrementCacheNumber(failKey);
            String message = String.format("已连续 %d 次%s，还剩 %d 次机会", count, statusCode.getMessage(), VALIDATE_MAX_RETRY_COUNT - count);
            throw new GlobalServiceException(message, GlobalServiceStatusCode.PARAM_FAILED_VALIDATE);
        }
        // 成功删除缓存
        redisCache.deleteObject(failKey);
    }
}
