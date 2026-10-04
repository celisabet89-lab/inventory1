package com.gestion.inventory.useCase.proveedor;

import com.gestion.inventory.sevice.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeleteProveedor {

    @Autowired
    private IProveedorService proveedorService;

    public void execute(Long idProveedor) {
        // Verificar que el proveedor existe
        if (proveedorService.getById(idProveedor) == null) {
            throw new IllegalArgumentException("Proveedor no encontrado con ID: " + idProveedor);
        }
        proveedorService.deleteById(idProveedor);
    }
}