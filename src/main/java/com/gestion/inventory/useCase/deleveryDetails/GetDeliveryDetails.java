package com.gestion.inventory.useCase.deleveryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.DeliveryDetails;
import com.gestion.inventory.sevice.IDeliveryDetailsService;

@Service
public class GetDeliveryDetails {
    @Autowired
    IDeliveryDetailsService deliveryDetailsService;
    public DeliveryDetails execute(Long id){
        return deliveryDetailsService.getById(id);
    }
}
