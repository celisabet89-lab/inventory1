package com.gestion.inventory.controller;

import com.gestion.inventory.domain.UnidadMedida;
import com.gestion.inventory.domain.dtorecust.UnidadMedidaRequest;
import com.gestion.inventory.useCase.unidadMedida.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/unidad_medida")
@CrossOrigin(origins = "*")
public class UnidadMedidaController {

    @Autowired CreateUnidadMedida  createUnidadMedida;
    @Autowired GetUnidadMedida     getUnidadMedida;
    @Autowired DeleteUnidadMedida  deleteUnidadMedida;
    @Autowired GetAllUnidadMedida  getAllUnidadMedida;
    @Autowired UpdateUnidadMedida  updateUnidadMedida;

    @GetMapping
    public List<UnidadMedida> getAll() { return getAllUnidadMedida.execute(); }

    @GetMapping("/{idUnidad}")
    public UnidadMedida getById(@PathVariable Long idUnidad) { return getUnidadMedida.execute(idUnidad); }

    @PostMapping
    public UnidadMedida create(@RequestBody UnidadMedidaRequest unidadMedidaRequest) {
        return createUnidadMedida.execute(unidadMedidaRequest);
    }
    @PutMapping("/{idUnidad}")
    public UnidadMedida update(@PathVariable Long idUnidad, @RequestBody UnidadMedidaRequest unidadMedidaRequest) {
        return updateUnidadMedida.execute(idUnidad, unidadMedidaRequest);
    }
    @DeleteMapping("/{idUnidad}")
    public void deleteById(@PathVariable Long idUnidad) { deleteUnidadMedida.execute(idUnidad); }
}