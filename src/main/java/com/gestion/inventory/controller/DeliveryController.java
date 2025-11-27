package com.gestion.inventory.controller;

import com.gestion.inventory.domain.Delivery;
import com.gestion.inventory.domain.dtorecust.DeliveryRequest;
import com.gestion.inventory.useCase.delivery.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/delivery")
public class DeliveryController {
    @Autowired
    GetAllDelivery  getAllDelivery;
    @Autowired
    GetDelivery getDelivery;
    @Autowired
    CreateDelivery createDelivery;
    @Autowired
    UpdateDelivery updateDelivery;
    @Autowired
    DeleteDelivery deleteDelivery;

    @GetMapping
    public List<Delivery> getAll(){
        return getAllDelivery.execute();
    }
    @GetMapping("/{idDelivery}")
    public Delivery getById(@PathVariable("idDelivery") Long idDelivery) {
        return getDelivery.execute(idDelivery);
    }
    @PostMapping
    public Delivery save(@RequestBody DeliveryRequest  deliveryRequest) {
        return createDelivery.execute(deliveryRequest);
    }

    @PutMapping("/{idDelivery}")
    public Delivery update(@PathVariable Long idDelivery, @RequestBody DeliveryRequest deliveryRequest) {
        return updateDelivery.execute(idDelivery, deliveryRequest);
    }

    @DeleteMapping("/{idDelivery}")
    public void deleteById(@PathVariable Long idDelivery) {
        deleteDelivery.execute(idDelivery);
    }
}
