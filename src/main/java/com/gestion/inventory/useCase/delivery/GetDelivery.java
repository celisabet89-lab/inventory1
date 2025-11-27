package com.gestion.inventory.useCase.delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Delivery;
import com.gestion.inventory.sevice.IDeliveryService;

@Service
public class GetDelivery {
    @Autowired
    IDeliveryService deliveryService;
    public Delivery execute(Long id){
        return deliveryService.getById(id);
    }
}
