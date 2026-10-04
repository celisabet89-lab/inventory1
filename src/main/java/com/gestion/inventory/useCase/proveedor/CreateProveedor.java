package com.gestion.inventory.useCase.proveedor;

import com.gestion.inventory.domain.Proveedor;
import com.gestion.inventory.domain.dtorecust.ProveedorRequest;
import com.gestion.inventory.sevice.IProveedorService;
import com.gestion.inventory.sevice.IUserService;
import com.gestion.inventory.sevice.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateProveedor {

    @Autowired
    private IProveedorService proveedorService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProfileService profileService;

    public Proveedor execute(ProveedorRequest request) {
        // Validar campos obligatorios
        if (request.getEmpresa() == null || request.getEmpresa().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la empresa es obligatorio");
        }

        if (request.getContacto() == null || request.getContacto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del contacto es obligatorio");
        }

        if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }


        if (request.getDireccion() == null || request.getDireccion().trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria");
        }

        // Crear nuevo proveedor
        Proveedor proveedor = new Proveedor();
        proveedor.setEmpresa(request.getEmpresa());
        proveedor.setPais(request.getPais() != null ? request.getPais() : "Bolivia");
        proveedor.setContacto(request.getContacto());
        proveedor.setEmail(request.getEmail());
        proveedor.setCiNit(request.getCiNit());
        proveedor.setDireccion(request.getDireccion());
        proveedor.setEstado(request.getEstado() != null ? request.getEstado() : "ACTIVO");
        proveedor.setObservaciones(request.getObservaciones());


        if (request.getProfileId() != null) {
            proveedor.setProfile(profileService.getById(request.getProfileId()));
        }

        return proveedorService.save(proveedor);
    }
}