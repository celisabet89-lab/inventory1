package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.DeliveryDetails;

import java.util.List;

public interface IDeliveryDetailsService {
    List<DeliveryDetails> getAll();
    DeliveryDetails getById(Long id);
    void deleteById(Long id);
    DeliveryDetails save(DeliveryDetails deliveryDetails);
}
