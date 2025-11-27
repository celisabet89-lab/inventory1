package com.gestion.inventory.controller;

import java.util.List;

import com.gestion.inventory.domain.Movements;
import com.gestion.inventory.domain.dtorecust.MovementsRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.inventory.useCase.movements.CreateMovements;
import com.gestion.inventory.useCase.movements.DeleteMovements;
import com.gestion.inventory.useCase.movements.GetAllMovements;
import com.gestion.inventory.useCase.movements.GetMovements;
import com.gestion.inventory.useCase.movements.UpdateMovements;

@RestController
@RequestMapping("/api/movements")
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

    @GetMapping
    public List<Movements> getAll(){
        return getAllMovements.execute();
    }
    @GetMapping("/{idMovements}")
    public Movements getById(@PathVariable Long idMovements) {
        return getMovements.execute(idMovements);
    }
    @PostMapping
    public Movements save(@RequestBody MovementsRequest movementsRequest){
        return createMovements.execute(movementsRequest);
    }
    @PutMapping("/{idMovements}")
    public Movements update(@PathVariable Long idMovements, @RequestBody MovementsRequest movementsRequest) {
        return updateMovements.execute(idMovements, movementsRequest);
    }

    @DeleteMapping("/{idMovements}")
    public void deleteById(@PathVariable Long idMovements) {
        deleteMovements.execute(idMovements);
    }
}
