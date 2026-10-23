package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.dtorecust.AlertRequest;
import com.gestion.inventory.domain.dtorecust.SupplierOrderRequest;
import com.gestion.inventory.useCase.Alert.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alert")
@CrossOrigin(origins = "*")
public class AlertsController {

    @Autowired GetAllAlert    getAllAlert;
    @Autowired CreateAlert    createAlert;
    @Autowired UpdateAlert    updateAlert;
    @Autowired DeleteAlert    deleteAlert;
    @Autowired GetAlert       getAlert;
    @Autowired CheckStockAlerts  checkStockAlerts;
    @Autowired SendSupplierOrder sendSupplierOrder;

    @GetMapping
    public List<Alert> getAll() { return getAllAlert.execute(); }
    @GetMapping("/{idAlert}")
    public Alert getById(@PathVariable Long idAlert) { return getAlert.execute(idAlert); }
    @PostMapping
    public Alert save(@RequestBody AlertRequest alertRequest) { return createAlert.execute(alertRequest); }
    @PutMapping("/{idAlert}")
    public Alert update(@PathVariable Long idAlert, @RequestBody AlertRequest alertRequest) {
        return updateAlert.execute(idAlert, alertRequest);
    }
    @DeleteMapping("/{idAlert}")
    public void deleteById(@PathVariable Long idAlert) { deleteAlert.execute(idAlert); }
    @GetMapping("/check-stock")
    public List<Alert> checkStockGet()  { return checkStockAlerts.execute(); }
    @PostMapping("/check-stock")
    public List<Alert> checkStockPost() { return checkStockAlerts.execute(); }
    @PostMapping("/order-supplier")
    public Map<String, Object> orderSupplier(@RequestBody SupplierOrderRequest req) {
        return sendSupplierOrder.execute(req);
    }
}