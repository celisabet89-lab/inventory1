package com.gestion.inventory.useCase.assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Assignment;
import com.gestion.inventory.domain.dtorecust.AssignmentRequets;
import com.gestion.inventory.sevice.IAssignmentService;

@Service
public class UpdateAssignment {
    @Autowired
    IAssignmentService assignmentService;
    public Assignment execute(Long id, AssignmentRequets assignmentRequets) {
    Assignment assignmentUpdate=assignmentService.getById(id);
        assignmentUpdate.setDatos(assignmentRequets.getDatos());
        return assignmentService.save(assignmentUpdate);}
}
