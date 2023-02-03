package com.nikolai.softarex.domain.service;

import com.nikolai.softarex.domain.entity.QuestionnaireField;
import com.nikolai.softarex.domain.entity.User;
import com.nikolai.softarex.domain.entity.UserPasswordChange;
import com.nikolai.softarex.domain.interfaces.UserService;
import com.nikolai.softarex.domain.repository.UserRepository;
import com.nikolai.softarex.web.dto.UpdateProfileDto;
import com.nikolai.softarex.web.exception.UserAlreadyExistException;
import com.nikolai.softarex.web.exception.UserNotFoundException;
import com.nikolai.softarex.web.util.ExceptionMessageUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.nikolai.softarex.web.util.ExceptionMessageUtil.userNotFoundMsg;

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
    public void saveImmediately(User user) {
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional
    public QuestionnaireField addField(User user, QuestionnaireField field) {
        user.addQuestionnaireField(field);
        this.saveImmediately(user);
        var fields = user.getQuestionnaireFields();

        return fields.get(fields.size() - 1);
    }


    public QuestionnaireField addField(Integer userId, QuestionnaireField field) {
        var user = this.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userNotFoundMsg(userId)));


        return addField(user, field);
    }

    @Override
    public void changePassword(User user, UserPasswordChange passwordChange) {
        user.setPasswordChange(passwordChange);
        user.setPassword(passwordChange.getPassword());

        passwordChange.setPassword(null);
        passwordChange.setVerificationCode(null);

        userRepository.save(user);
    }


    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    @Transactional
    public void updateProfile(User user, UpdateProfileDto profile) {
        if (userRepository.findByEmail(profile.getNewEmail()).isPresent()
                && !profile.getOldEmail().equals(profile.getNewEmail())) {
            throw new UserAlreadyExistException(ExceptionMessageUtil.userAlreadyExistMsg(profile.getNewEmail()));
        }

        var newEmail = profile.getNewEmail();
        var newFirstName = profile.getFirstName();
        var newLastName = profile.getLastName();
        var newPhoneNumber = profile.getPhoneNumber();

        if (newEmail != null) {
            user.setEmail(newEmail);
        }

        if (newFirstName != null) {
            user.setFirstName(newFirstName);
        }

        if (newLastName != null) {
            user.setLastName(newLastName);
        }

        if (newPhoneNumber != null) {
            user.setPhoneNumber(newPhoneNumber);
        }

        userRepository.save(user);
    }


    @Override
    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public Optional<User> findByVerificationCode(String code) {
        return userRepository.findByVerificationCode(code);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

}
