package com.gestion.inventory.useCase.categoria;

import com.gestion.inventory.sevice.ICategoriaServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteCategoria {
    @Autowired
    ICategoriaServise  categoriaServise;
    public void execute(Long idCategoria){categoriaServise.delete(idCategoria);}
}
