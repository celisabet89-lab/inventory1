package com.gestion.inventory.controller;

import com.gestion.inventory.domain.LugarVenta;
import com.gestion.inventory.domain.dtorecust.LugarventaRequest;
import com.gestion.inventory.useCase.lugarVenta.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lugar_venta")
@CrossOrigin(origins = "*")
public class LugarVentaController {

    @Autowired private CreateLugarVenta  createLugarVenta;
    @Autowired private UpdateLugarVenta  updateLugarVenta;
    @Autowired private GetAllLugarVenta  getAllLugarVenta;
    @Autowired private GetLugarVenta     getLugarVenta;
    @Autowired private DeleteLugarVenta  deleteLugarVenta;

    @GetMapping
    public List<LugarVenta> getAll() { return getAllLugarVenta.execute(); }

    @GetMapping("/{idLugarVentaLugarVenta}")
    public LugarVenta getById(@PathVariable Long idLugarVenta) { return getLugarVenta.execute(idLugarVenta); }

    @PostMapping
    public LugarVenta create(@RequestBody LugarventaRequest req) { return createLugarVenta.execute(req); }

    @PutMapping("/{idLugarVenta}")
    public LugarVenta update(@PathVariable Long idLugarVenta, @RequestBody LugarventaRequest req) { return updateLugarVenta.execute(idLugarVenta, req); }

    @DeleteMapping("/{idLugarVenta}")
    public void deleteById(@PathVariable Long idLugarVenta) { deleteLugarVenta.execute(idLugarVenta); }
}