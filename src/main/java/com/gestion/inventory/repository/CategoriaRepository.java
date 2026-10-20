package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    java.util.Optional<Categoria> findFirstByNombreIgnoreCase(String nombre);
}