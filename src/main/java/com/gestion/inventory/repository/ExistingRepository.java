package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Existing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExistingRepository extends JpaRepository<Existing, Long> {
}
