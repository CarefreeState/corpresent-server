package com.macaron.corpresent.common.util.convert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.macaron.corpresent.domain.user.model.entity.User;
import com.macaron.corpresent.redis.cache.RedisCache;
import com.macaron.corpresent.redis.cache.RedisMapCache;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.serializer.SerializationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-13
 * Time: 17:34
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 避免 websocket 的相关报错
class JsonUtilTest {

    @Resource
    private RedisCache redisCache;

    @Resource
    private RedisMapCache redisMapCache;

    public String serialize(Object object) throws SerializationException {
        return JSON.toJSONString(object, SerializerFeature.WriteClassName);
    }

    public Object deserialize(String str) throws SerializationException {
        return JSON.parseObject(str);
    }

    @Test
    void toJson() {
        Map<String, Object> map = new HashMap<>(){{
            put("1", null);
            put("2", 2);
        }};
        redisMapCache.putAllOver("666", map);
        System.out.println(redisMapCache.get("666", "1", Object.class));
//        redisCache.setObject("666", null);
//        System.out.println(redisCache.isExists("666"));
//        System.out.println(redisCache.getObject("666", Long.class));
//        redisCache.setObject("666", "777");
//        System.out.println(redisCache.getObject("666", String.class));
//        User user = new User();
//        user.setId(1000L);
//        user.setUsername("6666666666");
//        redisCache.setObject("666", user);
//        User redisUser = redisCache.getObject("666", User.class).orElse(null);
//        System.out.println(redisUser.getId());
//        System.out.println(redisUser.getUsername());
//        Optional<Map<String, Object>> map = redisMapCache.getMap("emailIdentifyCodeMap:login:2040484356@qq.com", String.class, Object.class);
//
//        System.out.println(map.get().get("captchaCode"));
//        System.out.println(map.get().get("captchaCodeCnt"));

    }
}