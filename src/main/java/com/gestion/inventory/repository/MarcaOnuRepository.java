package com.gestion.inventory.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.inventory.domain.MarcaOnu;

public interface MarcaOnuRepository extends JpaRepository<MarcaOnu, Long> {
    Optional<MarcaOnu> findFirstByNombreIgnoreCase(String nombre);
}