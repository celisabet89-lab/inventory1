package com.gestion.inventory.useCase.Alert;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.dtorecust.AlertRequest;
import com.gestion.inventory.sevice.IAlertService;

@Service
public class CreateAlert {
    @Autowired
    IAlertService alertService;
    public Alert execute(AlertRequest alertRequest){
        Alert alert = new Alert();
        alert.setResultado(alertRequest.getResultado());
        alert.setDescripcion(alertRequest.getDescripcion());
        alert.setFecha(alertRequest.getFecha());
        return alertService.save(alert);
    }
}
