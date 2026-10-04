package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.dtorecust.MovementsRequest;
import com.gestion.inventory.useCase.movements.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movements")
@CrossOrigin(origins = "*")
public class MovementsController {
    @Autowired CreateMovements createMovements;
    @Autowired DeleteMovements deleteMovements;
    @Autowired GetAllMovements getAllMovements;
    @Autowired GetMovements getMovements;
    @Autowired UpdateMovements updateMovements;

    @GetMapping
    public List<Movements> getAll() { return getAllMovements.execute(); }

    @GetMapping("/{idMovements}")
    public Movements getById(@PathVariable Long idMovements) { return getMovements.execute(idMovements); }

    @PostMapping
    public Movements save(@RequestBody MovementsRequest movementsRequest) { return createMovements.execute(movementsRequest); }

    @PutMapping("/{idMovements}")
    public Movements update(@PathVariable Long idMovements, @RequestBody MovementsRequest movementsRequest) {
        return updateMovements.execute(idMovements, movementsRequest);
    }

    @DeleteMapping("/{idMovements}")
    public void deleteById(@PathVariable Long idMovements) { deleteMovements.execute(idMovements); }
}