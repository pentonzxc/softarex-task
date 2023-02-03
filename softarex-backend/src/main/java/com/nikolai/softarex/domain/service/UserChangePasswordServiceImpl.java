package com.nikolai.softarex.domain.service;


import com.nikolai.softarex.domain.entity.UserPasswordChange;
import com.nikolai.softarex.domain.interfaces.UserChangePasswordService;
import com.nikolai.softarex.domain.repository.UserPasswordChangeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public final class UserChangePasswordServiceImpl implements UserChangePasswordService {

    private final UserPasswordChangeRepository repository;


    @Autowired
    public UserChangePasswordServiceImpl(UserPasswordChangeRepository changePasswordRepository) {
        this.repository = changePasswordRepository;
    }


    public void save(UserPasswordChange passwordChange) {
        repository.save(passwordChange);
    }


    public Optional<UserPasswordChange> findByVerificationCode(String verificationCode) {
        return repository.findByVerificationCode(verificationCode);
    }


}
