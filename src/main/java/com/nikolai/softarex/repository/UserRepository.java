package com.nikolai.softarex.repository;

import com.nikolai.softarex.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User , Integer> {
    Optional<User> findUserById(Integer id);
    Optional<User> findUserByEmail(String email);

}
