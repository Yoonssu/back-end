package com.aeon.hadog.repository;

import com.aeon.hadog.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsById(String Id);
    boolean existsByNickname(String nickname);
    boolean existsByEmail(String email);
    Optional<User> findById(String Id);
    Optional<User> findByUserId(Long Id);

    Optional<User> findByUserId(Long Id);
    void deleteById(String Id);

    Optional<User> findByEmail(String email);

}
