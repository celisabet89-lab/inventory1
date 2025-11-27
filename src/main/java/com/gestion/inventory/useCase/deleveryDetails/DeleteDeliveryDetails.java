package com.gestion.inventory.useCase.deleveryDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IDeliveryDetailsService;

@Service
public class DeleteDeliveryDetails {
    @Autowired
    IDeliveryDetailsService deliveryDetailsService;
    public void execute(Long idDeleveryDetails){
        deliveryDetailsService.deleteById(idDeleveryDetails);
    }
}
