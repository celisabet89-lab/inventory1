package com.gestion.inventory.useCase.cliente;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.sevice.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCliente {
    @Autowired
    IClienteService clienteService;
    public Cliente execute(Long id){
        return clienteService.getById(id);
    }
}
