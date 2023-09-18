package com.codegym.ClimaxStoreSpring.repository;

import com.codegym.ClimaxStoreSpring.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserName(String userName);

    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    boolean existsUserByUserName(String userName);

    boolean existsUserByEmail(String email);

    boolean existsUserByPhoneNumber(String phoneNumber);
}
