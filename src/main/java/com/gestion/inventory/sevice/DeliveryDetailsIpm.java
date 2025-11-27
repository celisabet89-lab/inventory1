package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.DeliveryDetails;
import com.gestion.inventory.repository.DeliveryDetailsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeliveryDetailsIpm implements IDeliveryDetailsService {
    DeliveryDetailsRepository deliveryDetailsRepository;
    public DeliveryDetailsIpm(DeliveryDetailsRepository deliveryDetailsRepository) {
        this.deliveryDetailsRepository = deliveryDetailsRepository;}
    @Override
    public List<DeliveryDetails> getAll() {
        return deliveryDetailsRepository.findAll();}

    @Override
    public DeliveryDetails getById(Long id) {
        return deliveryDetailsRepository.findById(id).orElse(null);}

    @Override
    public void deleteById(Long id) {
        deliveryDetailsRepository.deleteById(id);}

    @Override
    public DeliveryDetails save(DeliveryDetails deliveryDetails) {
        return deliveryDetailsRepository.save(deliveryDetails);}
}
