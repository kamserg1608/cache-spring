package ru.example.redis_key_listener.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.cache.annotation.EnableCaching;
import ru.example.redis_key_listener.model.User;
import ru.example.redis_key_listener.service.RedisKeyspaceEventListener;

import java.time.Duration;

@Configuration
@Profile("redis")
@EnableRedisRepositories(enableKeyspaceEvents = RedisKeyValueAdapter.EnableKeyspaceEvents.ON_DEMAND,
        shadowCopy = RedisKeyValueAdapter.ShadowCopy.OFF)
@EnableCaching
public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String redisHost;

    @Value("${spring.data.redis.port}")
    private int redisPort;
//
//    @Bean
//    public RedisConnectionFactory redisConnectionFactory() {
//        return new LettuceConnectionFactory(redisHost, redisPort);
//    }
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisTemplate<String, User> redisTemplate() {
        RedisTemplate<String, User> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // Use JSON serialization
        Jackson2JsonRedisSerializer<User> serializer = new Jackson2JsonRedisSerializer<>(User.class);
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(serializer);
        template.afterPropertiesSet();

        return template;
    }

    @Bean
    public RedisMessageListenerContainer keyExpirationListenerContainer(
            RedisConnectionFactory connectionFactory,
            RedisKeyspaceEventListener redisExpireEventService) {
        RedisMessageListenerContainer listenerContainer = new RedisMessageListenerContainer();
        listenerContainer.setConnectionFactory(connectionFactory);
        listenerContainer.addMessageListener(
                redisExpireEventService,
                new PatternTopic("__keyevent@*:expired"));
        return listenerContainer;
    }

    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofMinutes(1)) // Set default TTL for cache
                .computePrefixWith(cacheName -> "Cache:")
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(new Jackson2JsonRedisSerializer<>(User.class)));

        return RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(cacheConfig)
//                .transactionAware()
                .build();
    }
}