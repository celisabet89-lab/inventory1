package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Pago;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByCodigoCompraOrderByFechaDesc(String codigoCompra);
    List<Pago> findAllByOrderByFechaDesc();
}
