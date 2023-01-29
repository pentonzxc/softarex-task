package com.nikolai.softarex.service;

import com.nikolai.softarex.entity.QuestionnaireField;
import com.nikolai.softarex.entity.QuestionnaireResponse;
import com.nikolai.softarex.interfaces.UserService;
import com.nikolai.softarex.entity.User;
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
    public QuestionnaireField addField(User user , QuestionnaireField field) {
        user.addQuestionnaireField(field);
        saveImmediately(user);
        return field;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


//    @Override
//    @Transactional
//    public void updateProfile(UpdateProfileDto profile) {
//        var oldEmail = profile.getOldEmail();
//        var sourceUser = userRepository.findByEmail(oldEmail)
//                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(oldEmail)));
//
//        var newEmail = profile.getNewEmail();
//        var newFirstName = profile.getFirstName();
//        var newLastName = profile.getLastName();
//        var newPhoneNumber = profile.getPhoneNumber();
//
//        if (newEmail != null) {
//            sourceUser.setEmail(newEmail);
//        }
//
//        if (newFirstName != null) {
//            sourceUser.setFirstName(newFirstName);
//        }
//
//        if (newLastName != null) {
//            sourceUser.setLastName(newLastName);
//        }
//
//        if (newPhoneNumber != null) {
//            sourceUser.setPhoneNumber(newPhoneNumber);
//        }
//
//        userRepository.save(sourceUser);
//    }

//    @Override
//    public void updatePassword(ChangePasswordDto passwords) {
//        var email = passwords.getUserEmail();
//        var user = userRepository.findByEmail(email)
//                .orElseThrow(() -> new EmailNotFoundException(emailNotFoundMsg(email)));
//        var encodePassword = passwordEncoder.encode(passwords.getNewPassword());
//
//        user.setPassword(encodePassword);
//    }

    @Override
    public boolean isEmailAvailable(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public Optional<User> findByVerificationCode(String code) {
        return userRepository.findByVerificationCode(code);
    }

}
