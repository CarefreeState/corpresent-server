package com.macaron.corpresent.redis.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
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
public class RedisSetCache {

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisCache redisCache;

    private final RedisCacheJsonSerializer redisCacheJsonSerializer;

    public <K, E> void addAll(final K key, final Set<E> set) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        Set<String> jsonSet = redisCacheJsonSerializer.toJson(set);
        log.info("存入 Redis 中的 Set 缓存\t[{}]-[{}]", jsonKey, jsonSet);
        String[] jsonArr = jsonSet.toArray(new String[0]);
        redisTemplate.opsForSet().add(jsonKey, jsonArr);
    }

    public <K, E> void addAllOver(final K key, final Set<E> set) {
        redisCache.deleteObject(key);
        addAll(key, set);
    }

    public <K, E> void init(final K key, final Set<E> set, final long timeout, final TimeUnit timeUnit) {
        addAllOver(key, set);
        redisCache.expire(key, timeout, timeUnit);
    }

    public <K, E> Optional<Set<E>> getSet(final K key, final Class<E> eClazz) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        Set<String> jsonSet = redisTemplate.opsForSet().members(jsonKey);
        log.info("获取 Redis 中的 Set 缓存\t[{}]-[{}]", jsonKey, jsonSet);
        Set<E> set = redisCacheJsonSerializer.parse(jsonSet, eClazz);
        return Optional.ofNullable(set).filter(s -> !s.isEmpty());
    }

    public <K, E> Boolean contains(final K key, final E e) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        String jsonE = redisCacheJsonSerializer.toJson(key);
        Boolean flag = getSet(jsonKey, e.getClass()).map(set -> set.contains(jsonE)).orElse(Boolean.FALSE);
        log.info("查询 Redis 的 Set 的值是否存在\t[{}.{}]-[{}]", jsonKey, jsonE, flag);
        return flag;
    }

    public <K, E> void remove(final K key, final E e) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        String jsonE = redisCacheJsonSerializer.toJson(key);
        log.info("删除 Redis 中的 Set 的值\tkey[{}.{}]", jsonKey, jsonE);
        redisTemplate.opsForSet().remove(jsonKey, jsonE);
    }

}
