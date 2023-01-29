package com.nikolai.softarex.interfaces;

import com.nikolai.softarex.entity.QuestionnaireField;
import com.nikolai.softarex.entity.QuestionnaireResponse;
import com.nikolai.softarex.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    void save(User user);

    void saveImmediately(User user);

    QuestionnaireField addField(User user , QuestionnaireField field);


//    void updateProfile(UpdateProfileDto profile);

//    void updatePassword(ChangePasswordDto passwords);

    boolean isEmailAvailable(String email);

    Optional<User> findByVerificationCode(String code);

}
