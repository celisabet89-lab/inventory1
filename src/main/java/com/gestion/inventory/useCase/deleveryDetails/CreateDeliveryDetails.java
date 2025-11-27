package com.gestion.inventory.useCase.deleveryDetails;


import com.gestion.inventory.domain.DeliveryDetails;
import com.gestion.inventory.domain.dtorecust.DeliveryDetailsRequest;
import com.gestion.inventory.sevice.IDeliveryDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateDeliveryDetails {
    @Autowired
    IDeliveryDetailsService deliveryDetailsService;
    public DeliveryDetails execute(DeliveryDetailsRequest deliveryDetailsRequest) {
        DeliveryDetails deliveryDetails = new DeliveryDetails();
        deliveryDetails.setCantidad(deliveryDetailsRequest.getCantidad());
        return deliveryDetailsService.save(deliveryDetails);
    }
}
