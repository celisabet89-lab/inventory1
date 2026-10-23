package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Categoria;
import com.gestion.inventory.domain.dtorecust.CategoriaRequest;
import com.gestion.inventory.useCase.categoria.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categoria")
@CrossOrigin(origins = "*")
public class CategoriaController {

    @Autowired CreateCategoria  createCategoria;
    @Autowired DeleteCategoria  deleteCategoria;
    @Autowired GetAllCategoria  getAllCategoria;
    @Autowired UpdateCategoria  updateCategoria;
    @Autowired GetCategoria     getCategoria;

    @GetMapping
    public List<Categoria> getAll() { return getAllCategoria.execute(); }
    @GetMapping("/{idCategoria}")
    public Categoria getById(@PathVariable Long idCategoria) { return getCategoria.execute(idCategoria); }
    @PostMapping
    public Categoria save(@RequestBody CategoriaRequest categoriaRequest) { return createCategoria.execute(categoriaRequest); }
    @PutMapping("/{idCategoria}")
    public Categoria update(@PathVariable Long idCategoria, @RequestBody CategoriaRequest categoriaRequest) {
        return updateCategoria.execute(idCategoria, categoriaRequest);
    }
    @DeleteMapping("/{idCategoria}")
    public void delete(@PathVariable Long idCategoria) { deleteCategoria.execute(idCategoria); }
}