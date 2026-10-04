package com.gestion.inventory.useCase.unidadMedida;

import com.gestion.inventory.sevice.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class DeleteUnidadMedida {
    @Autowired
    IUnidadMedidaService unidadMedidaService;
    public void execute(Long idUnidad){unidadMedidaService.delete(idUnidad);}

}
