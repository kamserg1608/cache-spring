package ru.example.redis_key_listener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import ru.example.redis_key_listener.model.User;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Profile("redis")
public class UserServiceImpl implements UserService {
    private static final String KEY_PREFIX = "user:";
    private static final long EXPIRATION_TIME = 1L; // Expiration time in minutes

    private final RedisTemplate<String, User> redisTemplate;

    @Override
    public void saveUser(String id, User user) {
        redisTemplate.opsForValue().set(buildKey(id), user, EXPIRATION_TIME, TimeUnit.MINUTES);
    }

    @Override
    public Optional<User> getUser(String id) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(buildKey(id)));
    }

    @Override
    public void deleteUser(String id) {
        redisTemplate.delete(buildKey(id));
    }

    private String buildKey(String id) {
        return KEY_PREFIX + id;
    }
}
