package com.gestion.inventory.useCase.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Rol;
import com.gestion.inventory.sevice.IRolService;

@Service
public class GetRol {
    @Autowired
    IRolService rolService;
    public Rol execute(Long id){
        return rolService.getById(id);
    }
}
