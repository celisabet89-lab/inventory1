package com.gestion.inventory.useCase.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.dtorecust.AlertRequest;
import com.gestion.inventory.sevice.IAlertService;

@Service
public class UpdateAlert {
    @Autowired
    IAlertService alertService;
    public Alert execute(Long id, AlertRequest alertRequest){
        Alert alertUpdate=alertService.getById(id);
        alertUpdate.setResultado(alertRequest.getResultado());
        alertUpdate.setDescripcion(alertRequest.getDescripcion());
        return alertService.save(alertUpdate);
    }
}
