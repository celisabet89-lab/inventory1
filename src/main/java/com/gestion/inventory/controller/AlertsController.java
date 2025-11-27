package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.dtorecust.AlertRequest;
import com.gestion.inventory.useCase.Alert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alert")
public class AlertsController {
    @Autowired
    GetAllAlert  getAllAlert;
    @Autowired
    CreateAlert  createAlert;
    @Autowired
    UpdateAlert updateAlert;
    @Autowired
    DeleteAlert deleteAlert;
    @Autowired
    private GetAlert getAlert;

    @GetMapping
    public List<Alert> getAll(){
        return getAllAlert.execute();
    }
    @GetMapping("/{idAlert}")
    public Alert getById(@PathVariable("idAlert") Long idAlert) {
        return getAlert.execute(idAlert);
    }
    @PostMapping
    public Alert save(@RequestBody AlertRequest alertRequest) {
        return createAlert.execute(alertRequest);
    }

    @PutMapping("/{idAlert}")
    public Alert update(@PathVariable Long idAlert, @RequestBody AlertRequest alertRequest) {
        return updateAlert.execute(idAlert, alertRequest);
    }

    @DeleteMapping("/{idAlert}")
    public void deleteById(@PathVariable Long idAlert) {
        deleteAlert.execute(idAlert);
    }
}
