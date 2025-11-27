package com.gestion.inventory.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.inventory.domain.Rol;
import com.gestion.inventory.domain.dtorecust.RolRequest;
import com.gestion.inventory.useCase.rol.CreateRol;
import com.gestion.inventory.useCase.rol.DeleteRol;
import com.gestion.inventory.useCase.rol.GetAllRol;
import com.gestion.inventory.useCase.rol.GetRol;
import com.gestion.inventory.useCase.rol.UpdateRol;

@RestController
@RequestMapping("/api/rol")
public class RolController {
    @Autowired
    CreateRol createRol;
    @Autowired
    DeleteRol deleteRol;
    @Autowired
    GetAllRol getAllRol;
    @Autowired
    GetRol getRol;
    @Autowired
    UpdateRol updateRol;

    @GetMapping
    public List<Rol> getAll(){
        return getAllRol.execute();
    }
    @GetMapping("/{idRol}")
    public Rol getById(@PathVariable("idRol") Long idRol) {
        return getRol.execute(idRol);
    }
    @PostMapping
    public Rol save(@RequestBody RolRequest rolRequest) {
        return createRol.execute(rolRequest);
    }

    @PutMapping("/{idRol}")
    public Rol update(@PathVariable Long idRol, @RequestBody RolRequest rolRequest) {
        return updateRol.execute(idRol, rolRequest);
    }

    @DeleteMapping("/{idRol}")
    public void deleteById(@PathVariable Long idRol) {
        deleteRol.execute(idRol);
    }
}
