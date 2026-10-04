package com.gestion.inventory.useCase.delivery;

import com.gestion.inventory.domain.*;
import com.gestion.inventory.domain.dtorecust.DeliveryRequest;
import com.gestion.inventory.repository.ClienteRepository;
import com.gestion.inventory.repository.UserRepository;
import com.gestion.inventory.sevice.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UpdateDelivery {

    @Autowired private IDeliveryService  deliveryService;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private UserRepository    userRepository;

    public Delivery execute(Long id, DeliveryRequest req) {
        Delivery d = deliveryService.getById(id);
        if (req.getEstado()          != null) d.setEstado(req.getEstado());
        if (req.getObservacion()     != null) d.setObservacion(req.getObservacion());
        if (req.getDireccionEntrega()!= null) d.setDireccionEntrega(req.getDireccionEntrega());
        if (req.getFecha()           != null) d.setFecha(req.getFecha());

        if (req.getClienteId() != null)
            clienteRepository.findById(req.getClienteId()).ifPresent(d::setCliente);
        if (req.getTecnicoId() != null)
            userRepository.findById(req.getTecnicoId()).ifPresent(d::setTecnico);

        return deliveryService.save(d);
    }
}