package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceIpm implements ICategoriaServise {

    @Autowired
    private CategoriaRepository repository;

    @Override
    public List<Categoria> getAll() { return repository.findAll(); }

    @Override
    public Categoria getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada: " + id));
    }

    @Override
    public Categoria save(Categoria categoria) { return repository.save(categoria); }

    @Override
    public void delete(Long id) { repository.deleteById(id); }
}
