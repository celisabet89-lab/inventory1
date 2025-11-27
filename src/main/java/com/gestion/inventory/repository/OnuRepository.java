package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Onu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OnuRepository extends JpaRepository<Onu, Long> {
}
