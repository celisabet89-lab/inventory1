package com.gestion.inventory.controller;

import com.gestion.inventory.domain.DeliveryDetails;
import com.gestion.inventory.domain.dtorecust.DeliveryDetailsRequest;
import com.gestion.inventory.useCase.deleveryDetails.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController @RequestMapping("/api/deliveryDetails")
public class DeliveryDetailsController {
    @Autowired
    CreateDeliveryDetails createDeliveryDetails;
    @Autowired
    DeleteDeliveryDetails deleteDeliveryDetails;
    @Autowired
    GetAllDeliveryDetails getAllDeliveryDetails;
    @Autowired
    GetDeliveryDetails getDeliveryDetails;
    @Autowired
    UpdateDeliveryDetails updateDeliveryDetails;
    @GetMapping
    public List<DeliveryDetails> getAll() {
        return getAllDeliveryDetails.execute();
    }

    @GetMapping("/{idDeliveryDetails}")
    public DeliveryDetails getById(@PathVariable Long idDeliveryDetails) {
        return getDeliveryDetails.execute(idDeliveryDetails);
    }

    @PostMapping
    public DeliveryDetails save(@RequestBody DeliveryDetailsRequest deliveryDetailsRequest) {
        return createDeliveryDetails.execute(deliveryDetailsRequest);
    }

    @PutMapping("/{idDeliveryDetails}")
    public DeliveryDetails update(@PathVariable Long idDeliveryDetails, @RequestBody DeliveryDetailsRequest deliveryDetailsRequest) {
        return updateDeliveryDetails.execute(idDeliveryDetails, deliveryDetailsRequest);
    }

    @DeleteMapping("/{idDeliveryDetails}")
    public void deleteById(@PathVariable Long idDeliveryDetails) {
        deleteDeliveryDetails.execute(idDeliveryDetails);
    }
}
