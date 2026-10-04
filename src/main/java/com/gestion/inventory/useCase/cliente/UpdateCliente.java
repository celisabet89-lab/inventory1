package com.gestion.inventory.useCase.cliente;

import com.gestion.inventory.domain.*;
import com.gestion.inventory.domain.dtorecust.ClienteRequest;
import com.gestion.inventory.repository.ClienteRepository;
import com.gestion.inventory.repository.Lugarventarepository;
import com.gestion.inventory.sevice.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateCliente {
    @Autowired
    private IClienteService clienteService;

    @Autowired
    private Lugarventarepository lugarventarepository;
    @Autowired
    private ClienteRepository clienteRepository;

    @Transactional
    public Cliente execute(Long id, ClienteRequest r) {
        Cliente cliente= clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + id));

        if (r.getNombre() != null) cliente.setNombre(r.getNombre());
        if (r.getApellido() != null) cliente.setApellido(r.getApellido());
        if (r.getCi() != null) cliente.setCi(r.getCi());
        if (r.getDireccion() != null) cliente.setDireccion(r.getDireccion());
        if (r.getEstado() != null) cliente.setEstado(r.getEstado());
        if (r.getFechaRegistro() != null) cliente.setFechaRegistro(r.getFechaRegistro());
        if (r.getGmail() != null) cliente.setGmail(r.getGmail());
        if (r.getTelefono() != null) cliente.setTelefono(r.getTelefono());
        if (r.getFechaRegistro() != null) cliente.setFechaRegistro(r.getFechaRegistro());
        if (r.getZona() != null) cliente.setZona(r.getZona());
        if (r.getObservaciones() != null) cliente.setObservaciones(r.getObservaciones());
        // Actualizar categoría
        if (r.getIdLugarVenta() != null) {
            LugarVenta lugarVenta = lugarventarepository.findById(r.getIdLugarVenta())
                    .orElseThrow(() -> new RuntimeException("Categoría no encontrada con ID: " + r.getIdLugarVenta()));
            cliente.setLugarVenta(lugarVenta);
        }

        return clienteService.save(cliente);
    }
}