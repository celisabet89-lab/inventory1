package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Rol;

import java.util.List;

public interface IRolService {
    List<Rol> getAll();
    Rol getById(Long id);
    void deleteById(Long id);
    Rol save(Rol rol);
}
