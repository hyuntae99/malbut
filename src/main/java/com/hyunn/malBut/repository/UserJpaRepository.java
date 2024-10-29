package com.hyunn.malBut.repository;

import com.hyunn.malBut.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByEmail(String email);

    boolean existsUserByEmail(String email);

    List<User> findAllByStatusTrue();

}