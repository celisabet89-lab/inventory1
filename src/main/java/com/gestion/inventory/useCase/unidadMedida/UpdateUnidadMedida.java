package com.gestion.inventory.useCase.unidadMedida;

import com.gestion.inventory.domain.UnidadMedida;
import com.gestion.inventory.domain.dtorecust.UnidadMedidaRequest;
import com.gestion.inventory.sevice.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateUnidadMedida {
    @Autowired
    IUnidadMedidaService unidadMedidaService;
    public UnidadMedida execute(Long id, UnidadMedidaRequest  unidadMedidaRequest){
       UnidadMedida unidadMedidaupdate= unidadMedidaService.getById(id);
       unidadMedidaupdate.setNombre(unidadMedidaRequest.getNombre());
       unidadMedidaupdate.setAbreviatura(unidadMedidaRequest.getAbreviatura());
       return unidadMedidaService.save(unidadMedidaupdate);
    }

}
