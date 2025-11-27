package com.gestion.inventory.useCase.onu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IOnuservice;

@Service
public class DeleteOnu {
    @Autowired
    IOnuservice onuService;
    public void execute(Long idOnu){
        onuService.deleteById(idOnu);
    }
}
