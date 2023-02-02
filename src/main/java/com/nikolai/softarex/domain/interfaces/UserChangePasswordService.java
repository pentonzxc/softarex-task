package com.nikolai.softarex.domain.interfaces;


import com.nikolai.softarex.domain.entity.UserPasswordChange;

import java.util.Optional;

public interface UserChangePasswordService {

    void save(UserPasswordChange passwordChange);


    Optional<UserPasswordChange> findByVerificationCode(String verificationCode);
}
