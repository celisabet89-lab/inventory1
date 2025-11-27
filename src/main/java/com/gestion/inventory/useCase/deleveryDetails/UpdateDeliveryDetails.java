package com.gestion.inventory.useCase.deleveryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.DeliveryDetails;
import com.gestion.inventory.domain.dtorecust.DeliveryDetailsRequest;
import com.gestion.inventory.sevice.IDeliveryDetailsService;


@Service
public class UpdateDeliveryDetails {
    @Autowired
    IDeliveryDetailsService deliveryDetailsService;
    public DeliveryDetails execute(Long id,DeliveryDetailsRequest deliveryDetailsRequest) {
        DeliveryDetails deliveryDetailsUpdate = deliveryDetailsService.getById(id);
        deliveryDetailsUpdate.setCantidad(deliveryDetailsRequest.getCantidad());
        return deliveryDetailsService.save(deliveryDetailsUpdate);
    }
}
