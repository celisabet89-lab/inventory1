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

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.dtorecust.ExistingRequest;
import com.gestion.inventory.useCase.existing.CreateExisting;
import com.gestion.inventory.useCase.existing.DeleteExisting;
import com.gestion.inventory.useCase.existing.GetAllExisting;
import com.gestion.inventory.useCase.existing.GetExisting;
import com.gestion.inventory.useCase.existing.UpdateExisting;

@RestController
@RequestMapping("/api/existing")  
public class ExistingController {

    @Autowired
    CreateExisting createExisting;

    @Autowired
    GetAllExisting getAllExisting;

    @Autowired
    GetExisting getExisting;

    @Autowired
    DeleteExisting deleteExisting;

    @Autowired
    UpdateExisting updateExisting;

    @GetMapping
    public List<Existing> getAll() {
        return getAllExisting.execute();
    }

    @GetMapping("/{idExisting}")
    public Existing getById(@PathVariable Long idExisting) {
        return getExisting.execute(idExisting);
    }

    @PostMapping
    public Existing save(@RequestBody ExistingRequest existingRequest) {
        return createExisting.execute(existingRequest);
    }

    @PutMapping("/{idExisting}")
    public Existing update(@PathVariable Long idExisting, @RequestBody ExistingRequest existingRequest) {
        return updateExisting.execute(idExisting, existingRequest);
    }

    @DeleteMapping("/{idExisting}")
    public void deleteById(@PathVariable Long idExisting) {
        deleteExisting.execute(idExisting);
    }
}
