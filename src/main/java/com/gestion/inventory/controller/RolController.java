package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Rol;
import com.gestion.inventory.domain.dtorecust.RolRequest;
import com.gestion.inventory.useCase.rol.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rol")
@CrossOrigin(origins = "*")   // BUG CORREGIDO: faltaba CrossOrigin
public class RolController {
    @Autowired CreateRol createRol;
    @Autowired DeleteRol deleteRol;
    @Autowired GetAllRol getAllRol;
    @Autowired GetRol getRol;
    @Autowired UpdateRol updateRol;

    @GetMapping
    public List<Rol> getAll() { return getAllRol.execute(); }

    @GetMapping("/{idRol}")
    public Rol getById(@PathVariable Long idRol) { return getRol.execute(idRol); }

    @PostMapping
    public Rol save(@RequestBody RolRequest rolRequest) { return createRol.execute(rolRequest); }

    @PutMapping("/{idRol}")
    public Rol update(@PathVariable Long idRol, @RequestBody RolRequest rolRequest) {
        return updateRol.execute(idRol, rolRequest);
    }

    @DeleteMapping("/{idRol}")
    public void deleteById(@PathVariable Long idRol) { deleteRol.execute(idRol); }
}