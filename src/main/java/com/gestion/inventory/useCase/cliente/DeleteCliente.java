package com.gestion.inventory.useCase.cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IClienteService;

@Service
public class DeleteCliente {
    @Autowired
    IClienteService clienteService;
    public void execute(Long idCliente){
        clienteService.deleteById(idCliente);
    }
}
