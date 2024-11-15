package com.macaron.corpresent.security.config;

import com.macaron.corpresent.common.util.convert.ObjectUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * SpringSecurity白名单资源路径配置
 * Created by macro on 2018/11/5.
 */
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "secure.ignored")
public class IgnoreUrlsConfig implements InitializingBean {

    private List<String> urls;

    @Override
    public void afterPropertiesSet() throws Exception {
        urls = ObjectUtil.distinctNonNullStream(urls).toList();
    }
}
