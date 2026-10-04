package com.gestion.inventory.useCase.proveedor;

import com.gestion.inventory.domain.Proveedor;
import com.gestion.inventory.sevice.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GetAllProveedor {

    @Autowired
    private IProveedorService proveedorService;

    public List<Proveedor> execute() {
        return proveedorService.getAll();
    }
}