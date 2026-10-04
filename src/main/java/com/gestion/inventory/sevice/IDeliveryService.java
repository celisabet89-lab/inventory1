package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Delivery;

import java.util.List;

public interface IDeliveryService {
    List<Delivery> getAll();
    Delivery getById(Long id);
    void deleteById(Long id);
    Delivery save(Delivery delivery);
}
