package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Delivery;
import com.gestion.inventory.repository.DeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class DeliveryServiceImp implements IDeliveryService {

    @Autowired private DeliveryRepository deliveryRepository;

    @Override public List<Delivery> getAll() { return deliveryRepository.findAll(); }

    @Override public Delivery getById(Long id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Entrega no encontrada"));
    }

    @Override public Delivery save(Delivery d) { return deliveryRepository.save(d); }

    @Override public void deleteById(Long id) { deliveryRepository.deleteById(id); }
}