package com.gestion.inventory.useCase.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IAssignmentService;

@Service
public class DeleteAssignment {
    @Autowired
    IAssignmentService assignmentService;
    public void execute(Long idAssignment){
        assignmentService.deleteById(idAssignment);
    }
}
