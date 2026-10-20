package com.gestion.inventory.repository;

import com.gestion.inventory.domain.OnuMovimiento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnuMovimientoRepository extends JpaRepository<OnuMovimiento, Long> {
    List<OnuMovimiento> findAllByOrderByFechaDesc();
    List<OnuMovimiento> findByOnu_IdOrderByFechaDesc(Long onuId);
}