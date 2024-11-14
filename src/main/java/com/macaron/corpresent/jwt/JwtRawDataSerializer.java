package com.macaron.corpresent.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.common.util.convert.JsonUtil;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 10:43
 */
@Component
public class JwtRawDataSerializer {

    public <T> T parse(String json, Class<T> clazz) {
        return Optional.ofNullable(json).map(j -> JsonUtil.parse(j, clazz)).orElse(null);
    }

    public String toJson(Object obj) {
        return JsonUtil.toJson(obj);
    }

}
