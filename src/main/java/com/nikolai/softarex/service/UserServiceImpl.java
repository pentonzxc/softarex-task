package com.nikolai.softarex.service;

import com.nikolai.softarex.model.User;
import com.nikolai.softarex.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return userRepository.findUserById(id);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        this.save(user);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return userRepository.findUserByEmail(email).isEmpty();
    }
}
