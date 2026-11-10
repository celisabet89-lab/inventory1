package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Cliente;
import com.gestion.inventory.domain.dtorecust.ClienteRequest;
import com.gestion.inventory.useCase.cliente.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import com.gestion.inventory.repository.ClienteRepository;

@RestController
@RequestMapping("/api/cliente")
@CrossOrigin(origins = "*")
public class ClienteController {

    @Autowired CreateCliente createCliente;
    @Autowired UpdateCliente updateCliente;
    @Autowired DeleteCliente deleteCliente;
    @Autowired GetAllCliente getAllCliente;
    @Autowired GetCliente    getCliente;
    @Autowired ClienteRepository clienteRepo;

    @PatchMapping("/{idCliente}/estado")
    public ResponseEntity<?> cambiarEstado(@PathVariable Long idCliente, @RequestBody Map<String, String> body) {
        String estado = body.get("estado") == null ? "" : body.get("estado").trim().toUpperCase();
        if (!estado.equals("ACTIVO") && !estado.equals("INACTIVO")) {
            return ResponseEntity.badRequest().body(Map.of("message", "Estado inválido"));
        }
        return clienteRepo.findById(idCliente).<ResponseEntity<?>>map(c -> {
            c.setEstado(estado);
            return ResponseEntity.ok(clienteRepo.save(c));
        }).orElseGet(() -> ResponseEntity.status(404).body(Map.of("message", "Cliente no encontrado")));
    }

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