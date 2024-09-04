package com.colak.springtutorial.stringvalue.config.lettuce.sentinel;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
// @ConfigurationProperties(prefix = "redis")
public class LettuceConnectionFactoryConfig {
    private String master;
    private String password;

    private String nodes;

    // @Bean
    // @ConditionalOnProperty(name = "redis.enabled", havingValue = "true")
    public RedisConnectionFactory lettuceConnectionFactory() {
        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration();
        sentinelConfig.master(master).setPassword(RedisPassword.of(password));

        String[] redisNodes = nodes.split(",");

        for (String redisNode : redisNodes) {
            String[] hostAndPort = redisNode.split(":");
            sentinelConfig.addSentinel(new RedisNode(hostAndPort[0], Integer.parseInt(hostAndPort[1])));
        }

        return new LettuceConnectionFactory(sentinelConfig);
    }
}