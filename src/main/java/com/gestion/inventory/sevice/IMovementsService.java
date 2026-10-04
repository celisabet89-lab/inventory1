package com.gestion.inventory.useCase.sevice;

import com.gestion.inventory.domain.Movements;

import java.util.List;

public interface IMovementsService {
    List<Movements> getAll();
    Movements getById(Long id);
    void deleteById(Long id);
    Movements save(Movements movements);
}
