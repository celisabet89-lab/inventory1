package com.gestion.inventory.useCase.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IRolService;

@Service
public class DeleteRol {
    @Autowired
    IRolService rolService;
    public void execute(Long idRol){
        rolService.deleteById(idRol);
    }
}
