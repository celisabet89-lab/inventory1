package com.gestion.inventory.useCase.sevice;

import com.gestion.inventory.domain.Rol;

import java.util.List;

public interface IRolService {
    List<Rol> getAll();
    Rol getById(Long id);
    void deleteById(Long id);
    Rol save(Rol rol);
}
