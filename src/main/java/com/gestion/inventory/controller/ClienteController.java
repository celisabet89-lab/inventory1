package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.domain.dtorecust.ClienteRequest;
import com.gestion.inventory.useCase.cliente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")   // FIX: permite 192.168.0.103:4200
public class ClienteController {

    @Autowired CreateCliente createCliente;
    @Autowired UpdateCliente updateCliente;
    @Autowired DeleteCliente deleteCliente;
    @Autowired GetAllCliente getAllCliente;
    @Autowired GetCliente    getCliente;

    @GetMapping
    public List<Cliente> getAll() { return getAllCliente.execute(); }
    @GetMapping("/{idCliente}")
    public Cliente getById(@PathVariable Long idCliente) { return getCliente.execute(idCliente); }
    @PostMapping
    public Cliente save(@RequestBody ClienteRequest req) { return createCliente.execute(req); }
    @PutMapping("/{idCliente}")
    public Cliente update(@PathVariable Long idCliente, @RequestBody ClienteRequest req) {
        return updateCliente.execute(idCliente, req);
    }
    @DeleteMapping("/{idCliente}")
    public void deleteById(@PathVariable Long idCliente) { deleteCliente.execute(idCliente); }
}