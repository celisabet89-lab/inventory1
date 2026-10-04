package com.gestion.inventory.useCase.sevice;

import com.gestion.inventory.domain.Cliente;

import java.util.List;

public interface IClienteService {
    List<Cliente> getAll();
    Cliente getById(Long id);
    void deleteById(Long id);
    Cliente save(Cliente cliente);
}
