package com.gestion.inventory.repository;

import com.gestion.inventory.domain.UnidadMedida;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnidadMedidaRepository extends JpaRepository<UnidadMedida, Long> {
    java.util.Optional<UnidadMedida> findFirstByAbreviaturaIgnoreCase(String abreviatura);
}