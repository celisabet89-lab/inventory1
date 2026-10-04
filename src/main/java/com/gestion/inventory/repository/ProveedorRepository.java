package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Proveedor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProveedorRepository extends JpaRepository<Proveedor, Long> {

    Optional<Proveedor> findByEmpresa(String empresa);

    List<Proveedor> findByEstado(String estado);

    List<Proveedor> findByPais(String pais);

    @Query("SELECT p FROM Proveedor p WHERE p.empresa LIKE %:search% OR p.contacto LIKE %:search% OR p.email LIKE %:search%")
    List<Proveedor> search(@Param("search") String search);
}