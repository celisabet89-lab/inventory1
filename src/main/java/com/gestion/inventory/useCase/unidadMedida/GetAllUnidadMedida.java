package com.gestion.inventory.useCase.unidadMedida;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.domain.UnidadMedida;
import com.gestion.inventory.sevice.IUnidadMedidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllUnidadMedida {
    @Autowired
    IUnidadMedidaService UnidadMedidaService;
    public List<UnidadMedida> execute(){return UnidadMedidaService.getAll();}
}
