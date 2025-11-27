package com.gestion.inventory.useCase.cliente;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.sevice.IClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCliente {
    @Autowired
    IClienteService clienteService;
    public List<Cliente> execute(){
        return clienteService.getAll();
    }
}
