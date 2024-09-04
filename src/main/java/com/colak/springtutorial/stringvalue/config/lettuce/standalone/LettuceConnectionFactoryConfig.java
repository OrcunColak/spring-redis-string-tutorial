package com.colak.springtutorial.stringvalue.config.lettuce.standalone;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
// @ConfigurationProperties(prefix = "redis")
public class LettuceConnectionFactoryConfig {
    private String password;
    private String host;

    // @Bean
    // @ConditionalOnProperty(name = "redis.enabled", havingValue = "true")
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(host);
        redisStandaloneConfiguration.setPassword(password);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }
}
