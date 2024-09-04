package com.colak.springtutorial.stringvalue.config;

import io.lettuce.core.ClientOptions;
import io.lettuce.core.SslOptions;
import io.lettuce.core.protocol.ProtocolVersion;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

import java.io.IOException;

@Configuration
@RequiredArgsConstructor
public class LettuceConnectionFactoryConfig {

    private boolean sslEnabled;

    private String certFileLocation;

    private final ResourceLoader resourceLoader;

    // This is just an example to configure LettuceConnectionFactory
    // See https://medium.com/@shyamkrishnakumar/java-springboot-3-redis-configuration-with-redis-server-hosted-on-gcp-having-ssl-and-password-f470f78d9518
    // @Bean
    public LettuceConnectionFactory jedisConnectionFactory() throws IOException {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setPort(6379);
        redisStandaloneConfiguration.setPassword("password");

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder =
                LettuceClientConfiguration.builder();

        if (sslEnabled){
            SslOptions sslOptions = SslOptions.builder()
                    .trustManager(resourceLoader.getResource("file:" + certFileLocation).getFile())
                    .build();

            ClientOptions clientOptions = ClientOptions
                    .builder()
                    .sslOptions(sslOptions)
                    .protocolVersion(ProtocolVersion.RESP3)
                    .build();

            lettuceClientConfigurationBuilder
                    .clientOptions(clientOptions)
                    .useSsl().disablePeerVerification();
        }
        LettuceClientConfiguration lettuceClientConfiguration = lettuceClientConfigurationBuilder.build();

        return new LettuceConnectionFactory(redisStandaloneConfiguration, lettuceClientConfiguration);
    }
}
