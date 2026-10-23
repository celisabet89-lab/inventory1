package com.gestion.inventory.controller;

import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.gestion.inventory.domain.MarcaOnu;
import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.repository.MarcaOnuRepository;
import com.gestion.inventory.repository.OnuRepository;
import com.gestion.inventory.repository.ProductRepository;

@RestController
@RequestMapping("/api/marca-onu")
@CrossOrigin(origins = "*")
public class MarcaOnuController {

    private static final List<String> MARCAS_INICIALES = List.of("Huawei", "ZTE", "Nokia", "Fiberhome", "TP-LINK");

    @Autowired MarcaOnuRepository repo;
    @Autowired OnuRepository onus;
    @Autowired ProductRepository productos;

    @GetMapping
    @Transactional
    public List<MarcaOnu> getAll() {
        if (repo.count() == 0) {
            TreeSet<String> nombres = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
            nombres.addAll(MARCAS_INICIALES);
            for (Onu o : onus.findAll()) {
                if (o.getMarca() != null && !o.getMarca().isBlank()) nombres.add(o.getMarca().trim());
            }
            for (String n : nombres) {
                MarcaOnu m = new MarcaOnu();
                m.setNombre(n);
                repo.save(m);
            }
        }
        return repo.findAll();
    }

    @PostMapping
    public ResponseEntity<?> save(@RequestBody MarcaOnu body) {
        String nombre = body.getNombre() == null ? "" : body.getNombre().trim();
        if (nombre.isEmpty()) return error("El nombre es obligatorio");
        if (repo.findFirstByNombreIgnoreCase(nombre).isPresent()) return error("La marca \"" + nombre + "\" ya existe");
        body.setId(null);
        body.setNombre(nombre);
        return ResponseEntity.ok(repo.save(body));
    }

    /** Al renombrar una marca, también se renombran sus ONUs y su producto para que sigan enlazados. */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody MarcaOnu body) {
        MarcaOnu m = repo.findById(id).orElse(null);
        if (m == null) return error("Marca no encontrada");
        String nombre = body.getNombre() == null ? "" : body.getNombre().trim();
        if (nombre.isEmpty()) return error("El nombre es obligatorio");
        MarcaOnu otra = repo.findFirstByNombreIgnoreCase(nombre).orElse(null);
        if (otra != null && !otra.getId().equals(id)) return error("La marca \"" + nombre + "\" ya existe");

        String anterior = m.getNombre();
        if (anterior != null && !anterior.equals(nombre)) {
            for (Onu o : onus.findByMarcaIgnoreCase(anterior)) {
                o.setMarca(nombre);
                onus.save(o);
            }
            productos.findFirstByArticuloIgnoreCase(anterior).ifPresent(p -> {
                p.setArticulo(nombre);
                productos.save(p);
            });
        }
        m.setNombre(nombre);
        m.setDescripcion(body.getDescripcion());
        return ResponseEntity.ok(repo.save(m));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        MarcaOnu m = repo.findById(id).orElse(null);
        if (m == null) return error("Marca no encontrada");
        if (onus.existsByMarcaIgnoreCase(m.getNombre()))
            return error("No se puede eliminar: hay ONUs de la marca " + m.getNombre());
        repo.deleteById(id);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> error(String msg) {
        return ResponseEntity.badRequest().body(Map.of("message", msg));
    }
}