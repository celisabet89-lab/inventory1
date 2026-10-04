package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Proveedor;
import com.gestion.inventory.domain.dtorecust.ProveedorRequest;
import com.gestion.inventory.useCase.proveedor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/proveedor")
@CrossOrigin(origins = "http://localhost:4200")
public class ProveedorController {

    @Autowired
    CreateProveedor createProveedor;

    @Autowired
    DeleteProveedor deleteProveedor;

    @Autowired
    GetAllProveedor getAllProveedor;

    @Autowired
    GetProveedor getProveedor;

    @Autowired
    UpdateProveedor updateProveedor;



    @GetMapping
    public List<Proveedor> getAll() {
        return getAllProveedor.execute();
    }

    @GetMapping("/{idProveedor}")
    public ResponseEntity<Proveedor> getById(@PathVariable("idProveedor") Long idProveedor) {
        try {
            Proveedor proveedor = getProveedor.execute(idProveedor);
            return ResponseEntity.ok(proveedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Proveedor> create(@RequestBody ProveedorRequest request) {
        try {
            Proveedor proveedor = createProveedor.execute(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(proveedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{idProveedor}")
    public ResponseEntity<Proveedor> update(@PathVariable Long idProveedor, @RequestBody ProveedorRequest request) {
        try {
            Proveedor proveedor = updateProveedor.execute(idProveedor, request);
            return ResponseEntity.ok(proveedor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{idProveedor}")
    public ResponseEntity<Void> delete(@PathVariable Long idProveedor) {
        try {
            deleteProveedor.execute(idProveedor);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}