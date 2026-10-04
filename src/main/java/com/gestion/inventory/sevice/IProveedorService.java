package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Proveedor;
import java.util.List;

public interface IProveedorService {
    List<Proveedor> getAll();
    Proveedor getById(Long id);
    void deleteById(Long id);
    Proveedor save(Proveedor proveedor);
    }