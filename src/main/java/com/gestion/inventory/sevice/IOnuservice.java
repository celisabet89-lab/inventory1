package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Onu;

import java.util.List;

public interface IOnuservice {
    List<Onu> getAll();
    Onu getById(Long id);
    void deleteById(Long id);
    Onu save(Onu onu);
}
