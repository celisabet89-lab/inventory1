package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Delivery;
import com.gestion.inventory.domain.dtorecust.DeliveryRequest;
import com.gestion.inventory.sevice.IDeliveryService;
import com.gestion.inventory.useCase.delivery.CreateDelivery;
import com.gestion.inventory.useCase.delivery.UpdateDelivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
@CrossOrigin(origins = "*")
public class DeliveryController {

    @Autowired private IDeliveryService deliveryService;
    @Autowired private CreateDelivery   createDelivery;
    @Autowired private UpdateDelivery   updateDelivery;

    @GetMapping
    public List<Delivery> getAll() { return deliveryService.getAll(); }

    @GetMapping("/{id}")
    public Delivery getById(@PathVariable Long id) { return deliveryService.getById(id); }

    @PostMapping
    public Delivery create(@RequestBody DeliveryRequest req) { return createDelivery.execute(req); }

    @PutMapping("/{id}")
    public Delivery update(@PathVariable Long id, @RequestBody DeliveryRequest req) {
        return updateDelivery.execute(id, req);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { deliveryService.deleteById(id); }
}