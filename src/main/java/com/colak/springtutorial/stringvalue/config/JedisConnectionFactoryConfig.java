package com.colak.springtutorial.stringvalue.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

import java.util.List;

@Configuration
public class JedisConnectionFactoryConfig {

    @Value("${redis.config.isClustered: false}")
    private Boolean isClustered;

    @Value("${redis.config.cluster.nodes: null}")
    private List<String> nodes;

    // @Bean
    JedisConnectionFactory redisConnectionFactory() {
        if (isClustered) {
            // List<String> nodes = Collections.singletonList("****.***.****.****.cache.amazonaws.com:6379");
            RedisClusterConfiguration clusterConfiguration = new RedisClusterConfiguration(nodes);
            return new JedisConnectionFactory(clusterConfiguration);
        }

        // does not make sense to inject localhost host or loopback ip
        return new JedisConnectionFactory(new RedisStandaloneConfiguration("127.0.0.1", 6379));
    }
}
