package com.gestion.inventory.useCase.categoria;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.domain.dtorecust.CategoriaRequest;
import com.gestion.inventory.sevice.ICategoriaServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCategoria {
    @Autowired
    ICategoriaServise categoriaServise;

    public Categoria execute(CategoriaRequest categoriaRequest) {
        Categoria categoria = new Categoria();
        categoria.setNombre(categoriaRequest.getNombre());
        return categoriaServise.save(categoria);
    }
}