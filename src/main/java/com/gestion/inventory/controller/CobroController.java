
package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.domain.dtorecust.CobroRequest;
import com.gestion.inventory.useCase.cobro.CreateCobro;
import com.gestion.inventory.useCase.cobro.GetAllCobros;
import com.gestion.inventory.useCase.cobro.GetCobro;
import com.gestion.inventory.useCase.cobro.GetCobrosByCodigoVenta;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cobros")
@CrossOrigin(origins = "*")
public class CobroController {

    CreateCobro createCobro;
    GetAllCobros getAllCobros;
    GetCobro getCobroById;
    GetCobrosByCodigoVenta getCobrosByCodigoVenta;

    public CobroController(
            CreateCobro createCobro,
            GetAllCobros getAllCobros,
            GetCobro getCobroById,
            GetCobrosByCodigoVenta getCobrosByCodigoVenta) {

        this.createCobro = createCobro;
        this.getAllCobros = getAllCobros;
        this.getCobroById = getCobroById;
        this.getCobrosByCodigoVenta = getCobrosByCodigoVenta;
    }

    @GetMapping
    public ResponseEntity<List<Cobro>> getAll() {
        return ResponseEntity.ok(getAllCobros.execute());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(getCobroById.execute(id));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/venta/{codigoVenta}")
    public ResponseEntity<List<Cobro>> getByCodigoVenta(
            @PathVariable String codigoVenta) {

        return ResponseEntity.ok(
                getCobrosByCodigoVenta.execute(codigoVenta));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CobroRequest request) {
        try {
            Cobro cobro = createCobro.execute(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(cobro);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
