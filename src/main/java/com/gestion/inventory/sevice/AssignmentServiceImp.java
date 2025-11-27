package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Assignment;
import com.gestion.inventory.repository.AssignmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssignmentServiceImp implements IAssignmentService{
    AssignmentRepository assignmentRepository;
    public AssignmentServiceImp(AssignmentRepository assignmentRepository) {
        this.assignmentRepository = assignmentRepository;}
    @Override
    public List<Assignment> getAll() {
        return assignmentRepository.findAll();}

    @Override
    public Assignment getById(Long id) {
        return assignmentRepository.findById(id).orElse(null);}

    @Override
    public void deleteById(Long id) {
        assignmentRepository.deleteById(id);
    }

    @Override
    public Assignment save(Assignment assignment) {
        return assignmentRepository.save(assignment);
    }
}
