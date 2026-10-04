package com.gestion.inventory.useCase.categoria;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.sevice.ICategoriaServise;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCategoria {
    @Autowired
    ICategoriaServise categoriaServise;
    public List<Categoria> execute(){ return categoriaServise.getAll();}
}
