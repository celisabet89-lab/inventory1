package com.gestion.inventory.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestion.inventory.domain.ClienteOnu;
import com.gestion.inventory.domain.TipoClienteOnu;
import com.gestion.inventory.repository.ClienteOnuRepository;
import com.gestion.inventory.repository.TipoClienteOnuRepository;

@RestController
@RequestMapping("/api/cliente-onu")
@CrossOrigin(origins = "*")
public class ClienteOnuController {

    @Autowired ClienteOnuRepository repo;
    @Autowired TipoClienteOnuRepository tipos;

    @GetMapping
    public List<ClienteOnu> getAll() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return repo.findById(id).<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> error("Cliente no encontrado"));
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody ClienteOnu body) {
        String err = validar(body, null);
        if (err != null) return error(err);
        body.setId(null);
        body.setTipoCliente(resolverTipo(body.getTipoCliente()));
        return ResponseEntity.ok(repo.save(body));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ClienteOnu body) {
        ClienteOnu c = repo.findById(id).orElse(null);
        if (c == null) return error("Cliente no encontrado");
        String err = validar(body, id);
        if (err != null) return error(err);
        c.setNombre(body.getNombre().trim());
        c.setCi(body.getCi());
        c.setCelular(body.getCelular());
        c.setDireccion(body.getDireccion());
        c.setUbicacion(body.getUbicacion());
        c.setFechaNacimiento(body.getFechaNacimiento());
        c.setNumeroContrato(body.getNumeroContrato());
        c.setTipoCliente(resolverTipo(body.getTipoCliente()));
        return ResponseEntity.ok(repo.save(c));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private String validar(ClienteOnu b, Long idActual) {
        if (b.getNombre() == null || b.getNombre().isBlank()) return "El nombre es obligatorio";
        if (b.getNumeroContrato() != null) {
            boolean repetido = idActual == null
                    ? repo.existsByNumeroContrato(b.getNumeroContrato())
                    : repo.existsByNumeroContratoAndIdNot(b.getNumeroContrato(), idActual);
            if (repetido) return "El contrato N° " + b.getNumeroContrato() + " ya está registrado";
        }
        return null;
    }

    private TipoClienteOnu resolverTipo(TipoClienteOnu t) {
        if (t == null || t.getId() == null) return null;
        return tipos.findById(t.getId()).orElse(null);
    }

    private ResponseEntity<?> error(String msg) {
        return ResponseEntity.badRequest().body(Map.of("message", msg));
    }
}