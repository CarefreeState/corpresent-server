package com.macaron.corpresent.handler;

import com.baomidou.mybatisplus.extension.handlers.AbstractJsonTypeHandler;
import com.macaron.corpresent.common.exception.GlobalServiceException;
import com.macaron.corpresent.common.util.convert.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.util.Optional;

@MappedTypes({Object.class})
@MappedJdbcTypes({JdbcType.VARCHAR})
@Slf4j
public class MyBatisJsonTypeHandler extends AbstractJsonTypeHandler<Object> {

    private final Class<?> type;

    public MyBatisJsonTypeHandler(Class<?> type) {
        if (log.isTraceEnabled()) {
            log.trace("MyBatisJacksonTypeHandler({})", type);
        }
        Optional.ofNullable(type).orElseThrow(() ->
                new GlobalServiceException("Type argument cannot be null"));
        this.type = type;
    }

    @Override
    protected Object parse(String json) {
        return JsonUtil.parse(json, this.type);
    }

    @Override
    protected String toJson(Object obj) {
        return JsonUtil.toJson(obj);
    }
}
