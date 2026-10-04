package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.UnidadMedida;
import com.gestion.inventory.repository.UnidadMedidaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnidadMedidaServiceIpm implements IUnidadMedidaService {

    @Autowired
    private UnidadMedidaRepository repository;

    @Override
    public List<UnidadMedida> getAll() { return repository.findAll(); }

    @Override
    public UnidadMedida getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("UnidadMedida no encontrada: " + id));
    }

    @Override
    public UnidadMedida save(UnidadMedida unidadMedida) { return repository.save(unidadMedida); }

    @Override
    public void delete(Long id) { repository.deleteById(id); }
}
