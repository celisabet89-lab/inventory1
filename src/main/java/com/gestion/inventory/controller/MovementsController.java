package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.dtorecust.MovementsRequest;
import com.gestion.inventory.useCase.movements.AnularMovements;
import com.gestion.inventory.useCase.movements.CreateMovements;
import com.gestion.inventory.useCase.movements.DeleteMovements;
import com.gestion.inventory.useCase.movements.GetAllMovements;
import com.gestion.inventory.useCase.movements.GetMovements;
import com.gestion.inventory.useCase.movements.UpdateMovements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movements")
@CrossOrigin(origins = "*")
public class MovementsController {

    @Autowired
    CreateMovements createMovements;
    @Autowired
    DeleteMovements deleteMovements;
    @Autowired
    GetAllMovements getAllMovements;
    @Autowired
    GetMovements getMovements;
    @Autowired
    UpdateMovements updateMovements;
    @Autowired
    AnularMovements anularMovements;
    @GetMapping
    public List<Movements> getAll() {
        return getAllMovements.execute();
    }

    @GetMapping("/{idMovements}")
    public Movements getById(
            @PathVariable Long idMovements
    ) {
        return getMovements.execute(idMovements);
    }

    @PostMapping
    public Movements save(
            @RequestBody MovementsRequest movementsRequest
    ) {
        return createMovements.execute(movementsRequest);
    }

    @PutMapping("/{idMovements}")
    public Movements update(
            @PathVariable Long idMovements,
            @RequestBody MovementsRequest movementsRequest
    ) {
        return updateMovements.execute(
                idMovements,
                movementsRequest
        );
    }

    @PatchMapping("/{idMovements}/anular")
    public Movements anular(
            @PathVariable Long idMovements
    ) {
        return anularMovements.execute(idMovements);
    }

    @DeleteMapping("/{idMovements}")
    public void deleteById(
            @PathVariable Long idMovements
    ) {
        deleteMovements.execute(idMovements);
    }
}