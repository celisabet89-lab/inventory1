package com.gestion.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.gestion.inventory.domain.ClienteOnu;

public interface ClienteOnuRepository extends JpaRepository<ClienteOnu, Long> {
    boolean existsByNumeroContrato(Long numeroContrato);
    boolean existsByNumeroContratoAndIdNot(Long numeroContrato, Long id);
    boolean existsByTipoCliente_Id(Long tipoId);
}