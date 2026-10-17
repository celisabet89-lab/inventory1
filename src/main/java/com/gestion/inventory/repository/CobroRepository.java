package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Cobro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CobroRepository extends JpaRepository<Cobro, Long> {
    List<Cobro> findByCodigoVentaOrderByFechaDesc(String codigoVenta);
    List<Cobro> findAllByOrderByFechaDesc();
    List<Cobro> findByCodigoVentaAndEstadoIgnoreCase(String codigoVenta, String estado);
}
