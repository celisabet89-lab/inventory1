package com.gestion.inventory.useCase.cliente;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.domain.LugarVenta;
import com.gestion.inventory.domain.dtorecust.ClienteRequest;
import com.gestion.inventory.repository.Lugarventarepository;
import com.gestion.inventory.sevice.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;

@Service
public class CreateCliente {
    @Autowired
    private IClienteService clienteService;

    @Autowired
    private Lugarventarepository lugarventarepository;

    public Cliente execute(ClienteRequest r) {
        LugarVenta lugarVenta = null;
        if (r.getIdLugarVenta() != null) {
            lugarVenta = lugarventarepository.findById(r.getIdLugarVenta())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + r.getIdLugarVenta()));
        }
        Cliente c = new Cliente();
        c.setNombre(r.getNombre());  // Cambiado de setName a setNombre
        c.setApellido(r.getApellido());
        c.setGmail(r.getGmail());
        c.setTelefono(r.getTelefono());
        c.setDireccion(r.getDireccion());
        c.setCi(r.getCi());
        c.setZona(r.getZona());
        c.setEstado(r.getEstado() != null ? r.getEstado() : "ACTIVO");
        c.setObservaciones(r.getObservaciones());
        c.setFechaRegistro(LocalDate.now());
        c.setLugarVenta(lugarVenta);



        return clienteService.save(c);
    }
}