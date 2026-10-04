package com.gestion.inventory.useCase.unidadMedida;

import com.gestion.inventory.domain.UnidadMedida;
import com.gestion.inventory.domain.dtorecust.UnidadMedidaRequest;
import com.gestion.inventory.sevice.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateUnidadMedida {
    @Autowired
    IUnidadMedidaService unidadMedidaService;

    public UnidadMedida execute(UnidadMedidaRequest unidadMedidaRequest) {
        UnidadMedida unidadMedida = new UnidadMedida();
        unidadMedida.setNombre(unidadMedidaRequest.getNombre());
        unidadMedida.setAbreviatura(unidadMedidaRequest.getAbreviatura());
        return unidadMedidaService.save(unidadMedida);
    }
}