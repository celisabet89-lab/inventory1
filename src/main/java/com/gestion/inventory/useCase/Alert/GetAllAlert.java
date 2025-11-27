package com.gestion.inventory.useCase.Alert;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.sevice.IAlertService;

@Service
public class GetAllAlert {
    @Autowired
    IAlertService alertService;
    public List<Alert> execute(){
        return alertService.getAll();
    }
}
