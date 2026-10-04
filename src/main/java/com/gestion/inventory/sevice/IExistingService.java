package com.gestion.inventory.sevice;

import java.util.List;

import com.gestion.inventory.domain.Existing;

public interface IExistingService {
    List<Existing> getAll();
    Existing getById(Long id);
    void deleteById(Long id);
    Existing save(Existing existing);
}
