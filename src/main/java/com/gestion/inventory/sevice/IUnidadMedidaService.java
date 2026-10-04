package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.UnidadMedida;
import java.util.List;

public interface IUnidadMedidaService {
    List<UnidadMedida> getAll();
    UnidadMedida getById(Long id);
    UnidadMedida save(UnidadMedida unidadMedida);
    void delete(Long id);
}