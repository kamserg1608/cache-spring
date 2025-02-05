package ru.example.redis_key_listener.service;

import ru.example.redis_key_listener.model.User;

import java.util.Optional;

public interface UserService {

    void saveUser(String id, User user);

    Optional<User> getUser(String id);

    void deleteUser(String id);
}
