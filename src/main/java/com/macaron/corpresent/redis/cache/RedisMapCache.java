package com.macaron.corpresent.redis.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 21:09
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class RedisMapCache {

    private final RedisTemplate<String, String> redisTemplate;

    private final RedisCache redisCache;

    private final RedisCacheJsonSerializer redisCacheJsonSerializer;

    public <K, HK, HV> void putAll(final K key, final Map<HK, HV> map) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        Map<String, String> jsonMap = redisCacheJsonSerializer.toJson(map);
        log.info("Map 存入 Redis\t[{}]-[{}]", jsonKey, jsonMap);
        redisTemplate.opsForHash().putAll(jsonKey, jsonMap);
    }

    public <K, HK, HV> void putAllOver(final K key, final Map<HK, HV> map) {
        redisCache.deleteObject(key);
        putAll(key, map);
    }

    public <K, HK, HV> void init(final K key, final Map<HK, HV> map, long timeout, final TimeUnit timeUnit) {
        putAllOver(key, map);
        redisCache.expire(key, timeout, timeUnit);
    }

    public <K, HK, HV> Optional<Map<HK, HV>> getMap(final K key, final Class<HK> hkClazz, final Class<HV> hvClazz) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        Map<String, String> jsonMap = redisTemplate.opsForHash().entries(jsonKey).entrySet().stream().collect(Collectors.toMap(
                entry -> String.valueOf(entry.getKey()),
                entry -> String.valueOf(entry.getValue()),
                (oldData, newData) -> newData
        ));
        log.info("获取 Redis 中的 Map 缓存\t[{}]-[{}]", jsonKey, jsonMap);
        Map<HK, HV> map = redisCacheJsonSerializer.parse(jsonMap, hkClazz, hvClazz);
        return Optional.ofNullable(map).filter(m -> !m.isEmpty());
    }

    public <K, HK, HV> void put(final K key, final HK hashKey, final HV hashValue) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        String jsonHashKey = redisCacheJsonSerializer.toJson(hashKey);
        String jsonHashValue = redisCacheJsonSerializer.toJson(hashValue);
        log.info("存入 Redis 的某个 Map\t[{}.{}]-[{}]", jsonKey, jsonHashKey, jsonHashValue);
        redisTemplate.opsForHash().put(jsonKey, jsonHashKey, jsonHashValue);
    }

    public <K, HK, HV> Optional<HV> get(final K key, final HK hashKey, final Class<HV> hvClazz) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        String jsonHashKey = redisCacheJsonSerializer.toJson(hashKey);
        String hashValue = (String) redisTemplate.opsForHash().get(jsonKey, jsonHashKey);
        log.info("获取 Redis 中的 Map 的键值\t[{}.{}]-[{}]", jsonKey, jsonKey, hashValue);
        return Optional.ofNullable(redisCacheJsonSerializer.parse(hashValue, hvClazz));
    }

    public <K, HK> long increment(final K key, final HK hashKey, final long delta) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        String jsonHashKey = redisCacheJsonSerializer.toJson(hashKey);
        long number = redisTemplate.opsForHash().increment(jsonKey, jsonHashKey, delta);
        log.info("Redis key[{}.{}] {} 后：{}", jsonKey, jsonHashKey, delta, number);
        return number;
    }

    public <K, HK> void remove(final K key, final HK hashKey) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        String jsonHashKey = redisCacheJsonSerializer.toJson(hashKey);
        log.info("删除 Redis 中的 Map 的键值\tkey[{}.{}]", jsonKey, jsonHashKey);
        redisTemplate.opsForHash().delete(jsonKey, jsonHashKey);
    }

    public <K, HK> Boolean containsKey(final K key, final HK hashKey) {
        String jsonKey = redisCacheJsonSerializer.toJson(key);
        String jsonHashKey = redisCacheJsonSerializer.toJson(hashKey);
        Boolean flag = redisTemplate.opsForHash().hasKey(jsonKey, jsonHashKey);
        log.info("查询 Redis 的 Map 的键值是否存在\t[{}.{}]-[{}]", jsonKey, jsonHashKey, flag);
        return flag;
    }

}
