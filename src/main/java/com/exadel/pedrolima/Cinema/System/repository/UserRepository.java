package com.exadel.pedrolima.Cinema.System.repository;

import com.exadel.pedrolima.entity.User;
import com.exadel.pedrolima.entity.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Override
    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    List<User> findByRole(UserRole role);

}
