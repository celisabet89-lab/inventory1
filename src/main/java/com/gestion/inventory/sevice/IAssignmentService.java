package com.gestion.inventory.useCase.sevice;

import com.gestion.inventory.domain.Assignment;

import java.util.List;

public interface IAssignmentService {
    List<Assignment> getAll();
    Assignment getById(Long id);
    void deleteById(Long id);
    Assignment save(Assignment  assignment);
}
