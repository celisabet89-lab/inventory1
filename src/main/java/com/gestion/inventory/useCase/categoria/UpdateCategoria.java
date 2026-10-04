package com.gestion.inventory.useCase.categoria;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.domain.dtorecust.CategoriaRequest;
import com.gestion.inventory.sevice.ICategoriaServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCategoria {
    @Autowired
    ICategoriaServise categoriaServise;
    public Categoria execute(Long id, CategoriaRequest categoriarequest){
       Categoria categoriaupdate = categoriaServise.getById(id);
       categoriaupdate.setNombre(categoriarequest.getNombre());
       return categoriaServise.save(categoriaupdate);
    }
}
