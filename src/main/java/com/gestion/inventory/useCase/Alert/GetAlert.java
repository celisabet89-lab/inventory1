package com.gestion.inventory.useCase.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.sevice.IAlertService;

@Service
public class GetAlert {
    @Autowired
    IAlertService alertService;
    public Alert execute(Long id){
        return alertService.getById(id);
    }
}
