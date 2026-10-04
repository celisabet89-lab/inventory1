package com.gestion.inventory.useCase.delivery;

import com.gestion.inventory.domain.*;
import com.gestion.inventory.domain.dtorecust.DeliveryRequest;
import com.gestion.inventory.repository.ClienteRepository;
import com.gestion.inventory.repository.UserRepository;
import com.gestion.inventory.repository.ProductRepository;
import com.gestion.inventory.sevice.IDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreateDelivery {

    @Autowired private IDeliveryService  deliveryService;
    @Autowired private ClienteRepository clienteRepository;
    @Autowired private UserRepository    userRepository;
    @Autowired private ProductRepository productRepository;

    @Transactional
    public Delivery execute(DeliveryRequest req) {
        Delivery d = new Delivery();
        d.setFecha(req.getFecha() != null ? req.getFecha() : LocalDate.now());
        d.setEstado(req.getEstado() != null ? req.getEstado() : "PENDIENTE");
        d.setObservacion(req.getObservacion());
        d.setDireccionEntrega(req.getDireccionEntrega());

        if (req.getClienteId() != null)
            clienteRepository.findById(req.getClienteId()).ifPresent(d::setCliente);

        if (req.getTecnicoId() != null)
            userRepository.findById(req.getTecnicoId()).ifPresent(d::setTecnico);

        // Guardar primero para obtener ID
        Delivery saved = deliveryService.save(d);

        // Código automático: E-00001
        saved.setCodigo("E-" + String.format("%05d", saved.getIdDelivery()));

        // Detalles
        if (req.getDetalles() != null && !req.getDetalles().isEmpty()) {
            List<DeliveryDetails> detalles = new ArrayList<>();
            for (var det : req.getDetalles()) {
                productRepository.findById(det.getProductId()).ifPresent(product -> {
                    DeliveryDetails dd = new DeliveryDetails();
                    dd.setDelivery(saved);
                    dd.setProduct(product);
                    dd.setCantidad(det.getCantidad());
                    detalles.add(dd);
                });
            }
            saved.setDetalles(detalles);
        }

        return deliveryService.save(saved);
    }
}