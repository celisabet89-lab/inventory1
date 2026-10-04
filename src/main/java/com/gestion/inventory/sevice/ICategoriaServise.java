package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Categoria;
import java.util.List;

public interface ICategoriaServise {
    List<Categoria> getAll();
    Categoria getById(Long id);
    Categoria save(Categoria categoria);
    void delete(Long id);
}