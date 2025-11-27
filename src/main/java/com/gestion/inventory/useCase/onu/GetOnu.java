package com.gestion.inventory.useCase.onu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Onu;
import com.gestion.inventory.sevice.IOnuservice;

@Service
public class GetOnu {
    @Autowired
    IOnuservice onuservice;
    public Onu execute(Long id){
        return onuservice.getById(id);
    }
}
