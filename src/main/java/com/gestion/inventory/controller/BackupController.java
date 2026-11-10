package com.gestion.inventory.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.gestion.inventory.sevice.BackupService;

/** Copias de seguridad: solo el administrador (lo exige AccesoPorRol). */
@RestController
@RequestMapping("/api/backup")
@CrossOrigin(origins = "*")
public class BackupController {

    @Autowired BackupService backup;

    @GetMapping
    public ResponseEntity<?> listar() {
        try {
            return ResponseEntity.ok(backup.listar());
        } catch (Exception e) {
            return error(e);
        }
    }

    @PostMapping
    public ResponseEntity<?> crear() {
        try {
            return ResponseEntity.ok(backup.crear());
        } catch (Exception e) {
            return error(e);
        }
    }

    @PostMapping("/{nombre}/verificar")
    public ResponseEntity<?> verificar(@PathVariable String nombre) {
        try {
            return ResponseEntity.ok(backup.verificar(nombre));
        } catch (Exception e) {
            return error(e);
        }
    }

    @GetMapping("/pruebas")
    public ResponseEntity<?> pruebas() {
        try {
            return ResponseEntity.ok(backup.pruebas());
        } catch (Exception e) {
            return error(e);
        }
    }

    @GetMapping("/{nombre}/descargar")
    public ResponseEntity<?> descargar(@PathVariable String nombre) {
        try {
            Path p = backup.ruta(nombre);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nombre + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(Files.size(p))
                    .body(new FileSystemResource(p));
        } catch (Exception e) {
            return error(e);
        }
    }

    private ResponseEntity<Map<String, String>> error(Exception e) {
        return ResponseEntity.badRequest().body(Map.of("message", e.getMessage() == null ? "Error" : e.getMessage()));
    }
}