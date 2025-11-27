package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Movements;
import org.springframework.data.jpa.repository.JpaRepository;


public interface MovementsRepository extends JpaRepository<Movements, Long> {
}
