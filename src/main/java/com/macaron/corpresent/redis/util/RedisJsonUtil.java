package com.macaron.corpresent.redis.util;

import com.macaron.corpresent.common.util.convert.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 17:36
 */
// 解决 fastjson 一直报错的问题
public class RedisJsonUtil {

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RedisJsonObject {

        private Class<?> clazz;

        private String json;

    }

    public static Object parse(String json) {
        RedisJsonObject redisJsonObject = JsonUtil.parse(json, RedisJsonObject.class);
        return JsonUtil.parse(redisJsonObject.getJson(), redisJsonObject.getClazz());
    }

    public static String toJson(Object obj) {
        RedisJsonObject redisJsonObject = RedisJsonObject.builder()
                .clazz(obj.getClass())
                .json(JsonUtil.toJson(obj))
                .build();
        return JsonUtil.toJson(redisJsonObject);
    }

}
