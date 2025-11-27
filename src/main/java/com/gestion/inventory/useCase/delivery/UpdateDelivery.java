package com.gestion.inventory.useCase.delivery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Delivery;
import com.gestion.inventory.domain.dtorecust.DeliveryRequest;
import com.gestion.inventory.sevice.IDeliveryService;

@Service
public class UpdateDelivery {
    @Autowired
    IDeliveryService deliveryService;
    public Delivery execute(Long id, DeliveryRequest deliveryRequest){
        Delivery deliveryUpdate= deliveryService.getById(id);
        deliveryUpdate.setObservacion(deliveryRequest.getObservacion());
        return deliveryService.save(deliveryUpdate);
    }
}
