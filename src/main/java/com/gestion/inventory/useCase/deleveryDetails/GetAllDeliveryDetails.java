package com.gestion.inventory.useCase.deleveryDetails;
import java.util.List;

import com.gestion.inventory.domain.DeliveryDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IDeliveryDetailsService;

@Service
public class GetAllDeliveryDetails {
    @Autowired
    IDeliveryDetailsService deliveryDetailsService;
    public List<DeliveryDetails> execute(){
        return deliveryDetailsService.getAll();
    }
}
