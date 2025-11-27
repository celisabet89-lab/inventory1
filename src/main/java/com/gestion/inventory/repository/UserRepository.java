package com.gestion.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.inventory.domain.User;
public interface UserRepository extends JpaRepository<User, Long> {
}
