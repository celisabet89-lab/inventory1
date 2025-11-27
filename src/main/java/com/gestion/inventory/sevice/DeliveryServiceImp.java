package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Delivery;
import com.gestion.inventory.repository.DeliveryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeliveryServiceImp implements IDeliveryService{
    DeliveryRepository deliveryRepository;
    public DeliveryServiceImp(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;}
    @Override
    public List<Delivery> getAll() {
        return deliveryRepository.findAll();}

    @Override
    public Delivery getById(Long id) {
        return deliveryRepository.findById(id).orElse(null);}

    @Override
    public void deleteById(Long id) {
        deliveryRepository.deleteById(id);}

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryRepository.save(delivery);}
}
