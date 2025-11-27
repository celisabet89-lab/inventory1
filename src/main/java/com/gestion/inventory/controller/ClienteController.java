package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.domain.dtorecust.ClienteRequest;
import com.gestion.inventory.useCase.cliente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cliente")
public class ClienteController {
    @Autowired
    GetAllCliente getAllCliente;
    @Autowired
    GetCliente getCliente;
    @Autowired
    CreateCliente createCliente;
    @Autowired
    UpdateCliente updateCliente;
    @Autowired
    DeleteCliente deleteCliente;

    @GetMapping
    public List<Cliente> getAll(){
        return getAllCliente.execute();
    }
    @GetMapping("/{idCliente}")
    public Cliente getById(@PathVariable("idCliente") Long idCliente) {
        return getCliente.execute(idCliente);
    }
    @PostMapping
    public Cliente save(@RequestBody ClienteRequest clienteRequest) {
        return createCliente.execute(clienteRequest);
    }

    @PutMapping("/{idCliente}")
    public Cliente update(@PathVariable("idCliente") Long idCliente, @RequestBody ClienteRequest clienteRequest) {return  updateCliente.execute(idCliente, clienteRequest);}


    @DeleteMapping("/{idCliente}")
    public void deleteById(@PathVariable Long idCliente) {
        deleteCliente.execute(idCliente);
    }
}
