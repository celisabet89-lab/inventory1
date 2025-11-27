package com.gestion.inventory.useCase.Alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IAlertService;

@Service
public class DeleteAlert {
    @Autowired
    IAlertService alertService;
    public void execute(Long idAlert){
        alertService.deleteById(idAlert);
    }
}
