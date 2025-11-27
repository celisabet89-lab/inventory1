package com.gestion.inventory.useCase.cliente;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.domain.dtorecust.ClienteRequest;
import com.gestion.inventory.sevice.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCliente {@Autowired
IClienteService clienteService;
    public Cliente execute(ClienteRequest clienteRequest) {
        Cliente cliente = new Cliente();
        cliente.setName(clienteRequest.getName());
        cliente.setApellido(clienteRequest.getApellido());
        cliente.setTelefono(clienteRequest.getTelefono());
        cliente.setDireccion(clienteRequest.getDireccion());
        return clienteService.save(cliente);
    }
}
