package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Assignment;
import com.gestion.inventory.domain.dtorecust.AssignmentRequets;
import com.gestion.inventory.useCase.assignment.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assignment")
public class AssignmentController {
    @Autowired
    GetAllAssignment getAllAssignment;
    @Autowired
    GetAssignment getAssignment;
    @Autowired
    CreateAssignment createAssignment;
    @Autowired
    UpdateAssignment  updateAssignment;
    @Autowired
    private DeleteAssignment deleteAssignment;

    @GetMapping
    public List<Assignment> getAll(){
        return getAllAssignment.execute();
    }
    @GetMapping("/{idAssignment}")
    public Assignment getById(@PathVariable("idAssignment") Long idAssignment) {
        return getAssignment.execute(idAssignment);
    }
    @PostMapping
    public Assignment save(@RequestBody AssignmentRequets assignmentRequets) {
        return createAssignment.execute(assignmentRequets);
    }

    @PutMapping("/{idAssignment}")
    public Assignment update(@PathVariable Long idAssignment, @RequestBody AssignmentRequets assignmentRequets) {
        return updateAssignment.execute(idAssignment, assignmentRequets);
    }

    @DeleteMapping("/{idAssignment}")
    public void deleteById(@PathVariable Long idAssignment) {
        deleteAssignment.execute(idAssignment);
    }
}
