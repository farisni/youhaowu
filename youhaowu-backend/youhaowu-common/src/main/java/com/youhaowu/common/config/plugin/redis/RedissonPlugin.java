package com.youhaowu.common.config.plugin.redis;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import java.io.IOException;

@AutoConfiguration
@ConditionalOnProperty(prefix = "spring.data.redis", name = "host")
public class RedissonPlugin {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson(@Value("${spring.data.redis.host}") String host,
                                   @Value("${spring.data.redis.port}") String port) throws IOException {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        return Redisson.create(config);
    }
}
