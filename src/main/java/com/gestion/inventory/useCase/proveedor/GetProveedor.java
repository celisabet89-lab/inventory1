package com.gestion.inventory.useCase.proveedor;

import com.gestion.inventory.domain.Proveedor;
import com.gestion.inventory.sevice.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetProveedor {

    @Autowired
    private IProveedorService proveedorService;

    public Proveedor execute(Long idProveedor) {
        Proveedor proveedor = proveedorService.getById(idProveedor);
        if (proveedor == null) {
            throw new IllegalArgumentException("Proveedor no encontrado con ID: " + idProveedor);
        }
        return proveedor;
    }
}