package ru.example.redis_key_listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
public class RedisKeyListenerApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisKeyListenerApplication.class, args);
    }

}
