package com.gestion.inventory.useCase.rol;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IRolService;
import com.gestion.inventory.domain.Rol;

@Service
public class GetAllRol {
    @Autowired
    IRolService rolService;
    public List<Rol> execute(){
        return rolService.getAll();
    }
}
