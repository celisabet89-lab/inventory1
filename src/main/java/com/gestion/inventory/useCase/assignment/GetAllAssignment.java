package com.gestion.inventory.useCase.assignment;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Assignment;
import com.gestion.inventory.sevice.IAssignmentService;

@Service
public class GetAllAssignment {
    @Autowired
    IAssignmentService assignmentService;
    public List<Assignment> execute(){
        return assignmentService.getAll();
    }
}
