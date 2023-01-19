package com.nikolai.softarex.service;

import com.nikolai.softarex.dto.UpdateProfileRequest;
import com.nikolai.softarex.model.User;
import com.nikolai.softarex.repository.UserRepository;
import jakarta.transaction.Transactional;
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
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    // future...
    @Override
    @Transactional
    public void update(User user) {
        var userOpt = findByEmail(user.getEmail());
        if (userOpt.isPresent()) {
            var sourceUser = userOpt.get();

            var newFirstName = user.getFirstName();
            var newLastName = user.getLastName();
            var newPhoneNumber = user.getPhoneNumber();


            if (newFirstName != null) {
                sourceUser.setFirstName(newFirstName);
            }

            if (newLastName != null) {
                sourceUser.setLastName(newLastName);
            }

            if (newPhoneNumber != null) {
                sourceUser.setPhoneNumber(newPhoneNumber);
            }
        }

        userRepository.save(user);
    }


    // future....
    @Override
    @Transactional
    public void update(UpdateProfileRequest profile) {
        var oldEmail = profile.getOldEmail();
        var sourceUser = userRepository.findByEmail(oldEmail).get();

        var newEmail = profile.getNewEmail();
        var newFirstName = profile.getFirstName();
        var newLastName = profile.getLastName();
        var newPhoneNumber = profile.getPhoneNumber();

        if (newEmail != null) {
            sourceUser.setEmail(newEmail);
        }

        if (newFirstName != null) {
            sourceUser.setFirstName(newFirstName);
        }

        if (newLastName != null) {
            sourceUser.setLastName(newLastName);
        }

        if (newPhoneNumber != null) {
            sourceUser.setPhoneNumber(newPhoneNumber);
        }

        userRepository.save(sourceUser);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public Optional<User> findByVerificationCode(String code) {
        return userRepository.findByVerificationCode(code);
    }

}
