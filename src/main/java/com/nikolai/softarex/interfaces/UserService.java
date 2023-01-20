package com.nikolai.softarex.interfaces;

import com.nikolai.softarex.dto.ChangePasswordRequest;
import com.nikolai.softarex.dto.UpdateProfileRequest;
import com.nikolai.softarex.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    void save(User user);


    void updateProfile(UpdateProfileRequest profile);

    void updatePassword(ChangePasswordRequest passwords);

    boolean isEmailAvailable(String email);

    Optional<User> findByVerificationCode(String code);

}
