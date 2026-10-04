package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Proveedor;
import com.gestion.inventory.repository.ProveedorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class ProveedorServiceImpl implements IProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorServiceImpl(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    @Override
    public List<Proveedor> getAll() {
        return proveedorRepository.findAll();
    }

    @Override
    public Proveedor getById(Long id) {
        return proveedorRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        proveedorRepository.deleteById(id);
    }

    @Override
    public Proveedor save(Proveedor proveedor) {
        return proveedorRepository.save(proveedor);
    }
}