package com.gestion.inventory.useCase.rol;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Rol;
import com.gestion.inventory.domain.dtorecust.RolRequest;
import com.gestion.inventory.sevice.IRolService;

@Service
public class CreateRol {
    @Autowired
    IRolService rolService;
    public Rol execute(RolRequest rolRequest){
        Rol rol=new Rol();
        rol.setCargo(rolRequest.getCargo());
        return rolService.save(rol);
    }
}
