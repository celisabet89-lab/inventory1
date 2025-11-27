package com.gestion.inventory.useCase.onu;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IOnuservice;
import com.gestion.inventory.domain.Onu;

@Service
public class GetAllOnu {
    @Autowired
    IOnuservice onuservice;
    public List <Onu> execute(){
        return onuservice.getAll();
    }
}
