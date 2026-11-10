package com.gestion.inventory.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.inventory.domain.AsignacionMaterial;

public interface AsignacionMaterialRepository extends JpaRepository<AsignacionMaterial, Long> {
    List<AsignacionMaterial> findByTecnicoIdOrderByFechaDesc(Long tecnicoId);
    List<AsignacionMaterial> findAllByOrderByFechaDesc();
}