package com.gestion.inventory.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestion.inventory.domain.TipoClienteOnu;
import com.gestion.inventory.repository.ClienteOnuRepository;
import com.gestion.inventory.repository.TipoClienteOnuRepository;

@RestController
@RequestMapping("/api/tipo-cliente-onu")
@CrossOrigin(origins = "*")
public class TipoClienteOnuController {

    @Autowired TipoClienteOnuRepository repo;
    @Autowired ClienteOnuRepository clientes;

    @GetMapping
    public List<TipoClienteOnu> getAll() { return repo.findAll(); }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody TipoClienteOnu body) {
        if (body.getNombre() == null || body.getNombre().isBlank())
            return error("El nombre es obligatorio");
        body.setId(null);
        return ResponseEntity.ok(repo.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody TipoClienteOnu body) {
        TipoClienteOnu t = repo.findById(id).orElse(null);
        if (t == null) return error("Tipo de cliente no encontrado");
        if (body.getNombre() == null || body.getNombre().isBlank())
            return error("El nombre es obligatorio");
        t.setNombre(body.getNombre());
        t.setDescripcion(body.getDescripcion());
        t.setColor(body.getColor());
        return ResponseEntity.ok(repo.save(t));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (clientes.existsByTipoCliente_Id(id))
            return error("No se puede eliminar: hay clientes con este tipo");
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> error(String msg) {
        return ResponseEntity.badRequest().body(Map.of("message", msg));
    }
}