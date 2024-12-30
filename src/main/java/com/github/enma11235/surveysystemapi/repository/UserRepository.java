package com.github.enma11235.surveysystemapi.repository;

import com.github.enma11235.surveysystemapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
}