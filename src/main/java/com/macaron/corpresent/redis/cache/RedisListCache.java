package com.macaron.corpresent.redis.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 21:13
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisListCache {

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisCache redisCache;

    private final RedisCacheJsonSerializer redisCacheJsonSerializer;

    public <K, E> void addAll(final K key, final List<E> list) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        List<String> jsonList = redisCacheJsonSerializer.toJson(list);
        log.info("存入 Redis 中的 List 缓存\t[{}]-[{}]", jsonKey, jsonList);
        redisTemplate.opsForList().rightPushAll(jsonKey, jsonList);
    }

    public <K, E> void addAllOver(final K key, final List<E> list) {
        redisCache.deleteObject(key);
        addAll(key, list);
    }

    public <K, E> void init(final K key, final List<E> list, final long timeout, final TimeUnit timeUnit) {
        addAllOver(key, list);
        redisCache.expire(key, timeout, timeUnit);
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public <K, E> Optional<List<E>> getList(final K key, final Class<E> eClazz) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        List<String> jsonList = redisTemplate.opsForList().range(jsonKey, 0, -1);
        log.info("获取 Redis 中的 List 缓存\t[{}]-[{}]", jsonKey, jsonList);
        List<E> list = redisCacheJsonSerializer.parse(jsonList, eClazz);
        return Optional.ofNullable(list).filter(l -> !l.isEmpty());
    }

}
