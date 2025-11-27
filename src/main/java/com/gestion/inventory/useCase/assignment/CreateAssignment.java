package com.gestion.inventory.useCase.assignment;

import com.gestion.inventory.domain.Assignment;
import com.gestion.inventory.domain.dtorecust.AssignmentRequets;
import com.gestion.inventory.sevice.IAssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateAssignment {
    @Autowired
    IAssignmentService assignmentService;
    public Assignment execute(AssignmentRequets  assignmentRequets) {
        Assignment assignment = new Assignment();
        assignment.setFecha(assignmentRequets.getFecha());
        assignment.setDatos(assignmentRequets.getDatos());
        return assignmentService.save(assignment);
    }
}
