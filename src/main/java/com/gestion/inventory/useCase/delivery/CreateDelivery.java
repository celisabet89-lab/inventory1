package com.gestion.inventory.useCase.delivery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Delivery;
import com.gestion.inventory.domain.dtorecust.DeliveryRequest;
import com.gestion.inventory.sevice.IDeliveryService;

@Service
public class CreateDelivery {
    @Autowired
    IDeliveryService deliveryService;
    public Delivery execute(DeliveryRequest deliveryRequest){
        Delivery delivery=new Delivery();
        delivery.setFecha(deliveryRequest.getFecha());
        delivery.setObservacion(deliveryRequest.getObservacion());
        return deliveryService.save(delivery);
    }
}
