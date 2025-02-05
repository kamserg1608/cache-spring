package ru.example.redis_key_listener.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ru.example.redis_key_listener.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Profile("!redis")
public class InMemoryUserService implements UserService {

    private final Map<String, User> userMap = new ConcurrentHashMap<>();

    @Override
    public void saveUser(String id, User user) {
        userMap.put(id, user);
    }

    @Override
    public Optional<User> getUser(String id) {
        return Optional.ofNullable(userMap.get(id));
    }

    @Override
    public void deleteUser(String id) {
        userMap.remove(id);
    }
}
