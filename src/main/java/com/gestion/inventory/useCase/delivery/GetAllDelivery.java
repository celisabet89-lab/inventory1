package com.gestion.inventory.useCase.delivery;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Delivery;
import com.gestion.inventory.sevice.IDeliveryService;

@Service
public class GetAllDelivery {
    @Autowired
    IDeliveryService deliveryService;
    public List<Delivery> execute(){
        return deliveryService.getAll();
    }
}
