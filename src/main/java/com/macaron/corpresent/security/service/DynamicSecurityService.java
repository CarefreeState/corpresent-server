package com.macaron.corpresent.security.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务接口
 * Created by macro on 2020/2/7.
 */
public interface DynamicSecurityService {
    /**
     * 加载资源 ANT 通配符和资源对应 MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
