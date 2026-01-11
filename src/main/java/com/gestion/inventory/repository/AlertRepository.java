package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AlertRepository extends JpaRepository<Alert, Long> {
    boolean existsByProductAndEstado(Product product, Alert.AlertStatus estado);

    List<Alert> findByEstado(Alert.AlertStatus estado);

    @Query("SELECT COUNT(a) FROM Alert a WHERE a.estado = :estado")
    long countByEstado(@Param("estado") Alert.AlertStatus estado);
}
