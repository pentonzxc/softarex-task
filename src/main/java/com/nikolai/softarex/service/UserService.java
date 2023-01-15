package com.nikolai.softarex.service;

import com.nikolai.softarex.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    void save(User user);

    void update(User user);
    boolean isEmailAvailable(String email);

    Optional<User> findByVerificationCode(String code);


}
