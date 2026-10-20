package com.gestion.inventory.controller;

import com.gestion.inventory.domain.OnuMovimiento;
import com.gestion.inventory.domain.dtorecust.OnuOperacionRequest;
import com.gestion.inventory.sevice.OnuOperacionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/onu-operaciones")
@CrossOrigin(origins = "*")
public class OnuOperacionController {

    private final OnuOperacionService service;

    public OnuOperacionController(OnuOperacionService service) {
        this.service = service;
    }

    @PostMapping("/asignar")
    public ResponseEntity<?> asignar(@RequestBody OnuOperacionRequest req) {
        try {
            return ResponseEntity.ok(service.asignar(req));
        } catch (IllegalArgumentException e) {
            return error(e);
        }
    }

    @PostMapping("/recojo")
    public ResponseEntity<?> recojo(@RequestBody OnuOperacionRequest req) {
        try {
            return ResponseEntity.ok(service.recojo(req));
        } catch (IllegalArgumentException e) {
            return error(e);
        }
    }

    @PostMapping("/diagnostico")
    public ResponseEntity<?> diagnostico(@RequestBody OnuOperacionRequest req) {
        try {
            return ResponseEntity.ok(service.diagnosticar(req));
        } catch (IllegalArgumentException e) {
            return error(e);
        }
    }

    @GetMapping("/movimientos")
    public List<OnuMovimiento> movimientos() {
        return service.movimientos();
    }

    @GetMapping("/movimientos/onu/{onuId}")
    public List<OnuMovimiento> movimientosDe(@PathVariable Long onuId) {
        return service.movimientosDe(onuId);
    }

    private ResponseEntity<Map<String, String>> error(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    }
}