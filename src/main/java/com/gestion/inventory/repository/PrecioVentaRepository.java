package com.gestion.inventory.repository;
import com.gestion.inventory.domain.PrecioVenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PrecioVentaRepository extends JpaRepository<PrecioVenta, Long> {}