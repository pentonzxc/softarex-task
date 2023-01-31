package com.nikolai.softarex.interfaces;

import com.nikolai.softarex.dto.UpdateProfileDto;
import com.nikolai.softarex.entity.QuestionnaireField;
import com.nikolai.softarex.entity.QuestionnaireResponse;
import com.nikolai.softarex.entity.User;
import com.nikolai.softarex.entity.UserPasswordChange;

import java.util.Optional;

public interface UserService {
    Optional<User> findById(Integer id);

    Optional<User> findByEmail(String email);

    void save(User user);

    void saveImmediately(User user);

    QuestionnaireField addField(User user , QuestionnaireField field);


    QuestionnaireField addField(Integer userId , QuestionnaireField field);

    void changePassword(User user, UserPasswordChange passwordChange);


    void updateProfile(UpdateProfileDto profile);


    boolean isEmailAvailable(String email);

    Optional<User> findByVerificationCode(String code);

}
