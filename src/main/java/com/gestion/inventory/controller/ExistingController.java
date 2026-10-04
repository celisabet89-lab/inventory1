package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.dtorecust.ExistingRequest;
import com.gestion.inventory.useCase.existing.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/existing")
@CrossOrigin(origins = "*")
public class ExistingController {
    @Autowired CreateExisting createExisting;
    @Autowired GetAllExisting getAllExisting;
    @Autowired GetExisting getExisting;
    @Autowired DeleteExisting deleteExisting;
    @Autowired UpdateExisting updateExisting;

    @GetMapping
    public List<Existing> getAll() { return getAllExisting.execute(); }

    @GetMapping("/{idExisting}")
    public Existing getById(@PathVariable Long idExisting) { return getExisting.execute(idExisting); }

    @PostMapping
    public Existing save(@RequestBody ExistingRequest existingRequest) { return createExisting.execute(existingRequest); }

    @PutMapping("/{idExisting}")
    public Existing update(@PathVariable Long idExisting, @RequestBody ExistingRequest existingRequest) {
        return updateExisting.execute(idExisting, existingRequest);
    }

    @DeleteMapping("/{idExisting}")
    public void deleteById(@PathVariable Long idExisting) { deleteExisting.execute(idExisting); }
}