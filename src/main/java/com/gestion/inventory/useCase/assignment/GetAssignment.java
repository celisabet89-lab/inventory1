package com.gestion.inventory.useCase.assignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Assignment;
import com.gestion.inventory.sevice.IAssignmentService;

@Service
public class GetAssignment {
    @Autowired
    IAssignmentService assignmentService;
    public Assignment execute(Long id){
        return assignmentService.getById(id);
    }
}
