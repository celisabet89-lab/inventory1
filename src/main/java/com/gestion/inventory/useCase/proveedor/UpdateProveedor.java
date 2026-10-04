package com.gestion.inventory.useCase.proveedor;

import com.gestion.inventory.domain.Proveedor;
import com.gestion.inventory.domain.dtorecust.ProveedorRequest;
import com.gestion.inventory.sevice.IProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateProveedor {

    @Autowired
    private IProveedorService proveedorService;

    public Proveedor execute(Long idProveedor, ProveedorRequest request) {
        // Obtener el proveedor existente
        Proveedor proveedorUpdate = proveedorService.getById(idProveedor);

        if (proveedorUpdate == null) {
            throw new IllegalArgumentException("Proveedor no encontrado con ID: " + idProveedor);
        }

        // Actualizar solo los campos que vienen en el request
        if (request.getEmpresa() != null && !request.getEmpresa().trim().isEmpty()) {
            proveedorUpdate.setEmpresa(request.getEmpresa());
        }

        if (request.getPais() != null) {
            proveedorUpdate.setPais(request.getPais());
        }

        if (request.getContacto() != null && !request.getContacto().trim().isEmpty()) {
            proveedorUpdate.setContacto(request.getContacto());
        }

        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            proveedorUpdate.setEmail(request.getEmail());
        }

        if (request.getCiNit() != null && !request.getCiNit().trim().isEmpty()) {
            proveedorUpdate.setCiNit(request.getCiNit());
        }

        if (request.getDireccion() != null && !request.getDireccion().trim().isEmpty()) {
            proveedorUpdate.setDireccion(request.getDireccion());
        }

        if (request.getEstado() != null && !request.getEstado().trim().isEmpty()) {
            proveedorUpdate.setEstado(request.getEstado());
        }

        if (request.getObservaciones() != null) {
            proveedorUpdate.setObservaciones(request.getObservaciones());
        }

        return proveedorService.save(proveedorUpdate);
    }
}