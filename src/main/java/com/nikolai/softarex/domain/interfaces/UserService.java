package com.nikolai.softarex.domain.interfaces;


import com.nikolai.softarex.domain.entity.QuestionnaireField;
import com.nikolai.softarex.domain.entity.User;
import com.nikolai.softarex.domain.entity.UserPasswordChange;
import com.nikolai.softarex.web.dto.UpdateProfileDto;

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
