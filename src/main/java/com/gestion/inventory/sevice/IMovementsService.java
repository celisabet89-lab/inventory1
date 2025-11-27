package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Movements;

import java.util.List;

public interface IMovementsService {
    List<Movements> getAll();
    Movements getById(Long id);
    void deleteById(Long id);
    Movements save(Movements movements);
}
