package com.nikolai.softarex.service;

import com.nikolai.softarex.dto.ChangePasswordRequest;
import com.nikolai.softarex.dto.UpdateProfileRequest;
import com.nikolai.softarex.exception.EmailNotFoundException;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.model.User;
import com.nikolai.softarex.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.nikolai.softarex.util.ExceptionMessageUtil.emailNotFoundMsg;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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


    @Override
    @Transactional
    public void updateProfile(UpdateProfileRequest profile) {
        var oldEmail = profile.getOldEmail();
        var sourceUser = userRepository.findByEmail(oldEmail)
                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(oldEmail)));

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
    public void updatePassword(ChangePasswordRequest passwords) {
        var email = passwords.getUserEmail();
        var user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(email)));
        var encodePassword = passwordEncoder.encode(passwords.getNewPassword());

        user.setPassword(encodePassword);
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
