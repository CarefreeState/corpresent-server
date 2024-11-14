package com.macaron.corpresent.domain.user.security;

import com.macaron.corpresent.domain.user.service.ResourceService;
import com.macaron.corpresent.security.service.DynamicSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-11-14
 * Time: 10:56
 */
@Service
@RequiredArgsConstructor
public class DynamicSecurityServiceImpl implements DynamicSecurityService {

    private final ResourceService resourceService;

    @Override
    public Map<String, ConfigAttribute> loadDataSource() {
        Map<String, ConfigAttribute> map = new ConcurrentHashMap<>();
        resourceService.list().forEach(resource -> {
            String config = String.format("%d:%s", resource.getId(), resource.getName());
            map.put(resource.getPath(), new SecurityConfig(config));
        });
        return map;
    }
}
