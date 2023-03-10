package com.nikolai.softarex.domain.repository;


import com.nikolai.softarex.domain.entity.User;
import com.nikolai.softarex.domain.entity.UserPasswordChange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserPasswordChangeRepository extends JpaRepository<UserPasswordChange, Integer> {

    Optional<UserPasswordChange> findByUser(User user);


    Optional<UserPasswordChange> findByVerificationCode(String verificationCode);
}
