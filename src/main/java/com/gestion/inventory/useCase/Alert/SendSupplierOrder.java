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

    @Autowired
    private IAlertService alertService;

    @Autowired
    private SupplierEmailService supplierEmailService;

    public Map<String, Object> execute(
            SupplierOrderRequest req
    ) {

        if (
                req.getSupplierEmail() == null ||
                        req.getSupplierEmail().isBlank()
        ) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email del proveedor requerido"
            );
        }

        if (
                req.getCantidadPedida() == null ||
                        req.getCantidadPedida() <= 0
        ) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La cantidad debe ser mayor a 0"
            );
        }

        if (
                req.getProductoNombre() == null ||
                        req.getProductoNombre().isBlank()
        ) {

            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "El producto es obligatorio"
            );
        }

        try {

            supplierEmailService.sendOrderEmail(req);

        } catch (Exception e) {

            throw new ResponseStatusException(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage(),
                    e
            );
        }

        if (
                req.getAlertId() != null
        ) {

            try {

                Alert alerta =
                        alertService.getById(
                                req.getAlertId()
                        );

                if (alerta != null) {

                    alerta.setEstado(
                            Alert.AlertStatus.GESTIONADA
                    );

                    alertService.save(
                            alerta
                    );
                }

            } catch (Exception e) {

                System.err.println(
                        "No se pudo actualizar la alerta: " +
                                e.getMessage()
                );
            }
        }

        return Map.of(
                "success",
                true,
                "message",
                "Pedido enviado correctamente a " +
                        req.getSupplierEmail()
        );
    }
}