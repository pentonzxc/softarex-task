package com.nikolai.softarex.service;

import com.nikolai.softarex.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserById(Integer id);

    Optional<User> findUserByEmail(String email);

    void save(User user);

    void update(User user);
    boolean isEmailAvailable(String email);
}
