package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.repository.ClienteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ClienteServiceIpm implements IClienteService {
    ClienteRepository clienteRepository;
    public ClienteServiceIpm(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;}
    @Override
    public List<Cliente> getAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente getById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        clienteRepository.deleteById(id);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
}
