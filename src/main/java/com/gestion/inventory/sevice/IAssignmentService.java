package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Assignment;

import java.util.List;

public interface IAssignmentService {
    List<Assignment> getAll();
    Assignment getById(Long id);
    void deleteById(Long id);
    Assignment save(Assignment  assignment);
}
