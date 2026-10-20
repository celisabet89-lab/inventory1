package com.gestion.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.inventory.domain.TipoClienteOnu;

public interface TipoClienteOnuRepository extends JpaRepository<TipoClienteOnu, Long> {
}