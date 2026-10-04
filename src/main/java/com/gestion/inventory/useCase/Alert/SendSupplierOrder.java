package com.gestion.inventory.useCase.Alert;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.dtorecust.SupplierOrderRequest;
import com.gestion.inventory.sevice.IAlertService;
import com.gestion.inventory.sevice.SupplierEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@Service
public class SendSupplierOrder {

    @Autowired private IAlertService       alertService;
    @Autowired private SupplierEmailService supplierEmailService;

    public Map<String, Object> execute(SupplierOrderRequest req) {
        // Validaciones básicas
        if (req.getSupplierEmail() == null || req.getSupplierEmail().isBlank())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email del proveedor requerido");
        if (req.getCantidadPedida() == null || req.getCantidadPedida() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cantidad debe ser mayor a 0");

        // Enviar email al proveedor
        supplierEmailService.sendOrderEmail(req);

        // Marcar la alerta como GESTIONADA si se proporcionó alertId
        if (req.getAlertId() != null) {
            try {
                Alert alerta = alertService.getById(req.getAlertId());
                alerta.setEstado(Alert.AlertStatus.GESTIONADA);
                alertService.save(alerta);
            } catch (Exception e) {
                System.err.println("No se pudo marcar alerta como gestionada: " + e.getMessage());
            }
        }

        return Map.of(
                "success", true,
                "message", "Pedido enviado a " + req.getSupplierEmail()
        );
    }
}