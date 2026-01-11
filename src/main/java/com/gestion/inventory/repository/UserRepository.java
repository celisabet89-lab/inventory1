package com.gestion.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.inventory.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<Object> findByEmail(String email);
}
