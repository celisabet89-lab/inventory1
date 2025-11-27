package com.gestion.inventory.useCase.cliente;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.domain.dtorecust.ClienteRequest;
import com.gestion.inventory.sevice.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateCliente {
    @Autowired
    IClienteService clienteService;
    public Cliente execute(Long id, ClienteRequest clienteRequest) {
        Cliente clienteUpdate = clienteService.getById(id);
        clienteUpdate.setName(clienteRequest.getName());
        clienteUpdate.setApellido(clienteRequest.getApellido());
        clienteUpdate.setTelefono(clienteRequest.getTelefono());
        clienteUpdate.setDireccion(clienteRequest.getDireccion());
        return clienteService.save(clienteUpdate);
    }
}
