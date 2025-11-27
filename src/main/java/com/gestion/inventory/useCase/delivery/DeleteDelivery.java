package com.gestion.inventory.useCase.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IDeliveryService;

@Service
public class DeleteDelivery {
    @Autowired
    IDeliveryService deliveryService;
    public void execute(Long idDelivery){
        deliveryService.deleteById(idDelivery);
    }
}
