package com.nikolai.softarex.interfaces;

import com.nikolai.softarex.dto.ChangePasswordDto;
import com.nikolai.softarex.dto.UpdateProfileDto;
import com.nikolai.softarex.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    void save(User user);


//    void updateProfile(UpdateProfileDto profile);

//    void updatePassword(ChangePasswordDto passwords);

    boolean isEmailAvailable(String email);

    Optional<User> findByVerificationCode(String code);

}
