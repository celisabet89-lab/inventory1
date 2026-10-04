package com.gestion.inventory.useCase.unidadMedida;

import com.gestion.inventory.domain.UnidadMedida;
import com.gestion.inventory.sevice.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetUnidadMedida {
    @Autowired
    IUnidadMedidaService unidadMedidaService;
    public UnidadMedida execute(Long id){return unidadMedidaService.getById(id);}
}
