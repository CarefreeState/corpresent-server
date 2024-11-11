package com.macaron.corpresent.config;

import com.macaron.corpresent.common.util.http.SnowflakeIdGenerator;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created With Intellij IDEA
 * Description:
 * User: 马拉圈
 * Date: 2024-09-30
 * Time: 17:33
 */
@Configuration
@Getter
@Setter
@ConfigurationProperties("macaron.request.id")
public class RequestIdConfig {

    private Long workerId;

    private String header;

    @Bean
    public SnowflakeIdGenerator requestIdGenerator() {
        return new SnowflakeIdGenerator(workerId);
    }

}
