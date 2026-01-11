package com.gestion.inventory.useCase.Alert;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.domain.dtorecust.AlertRequest;
import com.gestion.inventory.sevice.IAlertService;
import com.gestion.inventory.sevice.IProductService;
import org.springframework.stereotype.Service;

@Service
public class CreateAlert {

    private final IAlertService alertService;
    private final IProductService productService;

    public CreateAlert(IAlertService alertService,
                       IProductService productService) {
        this.alertService = alertService;
        this.productService = productService;
    }

    public Alert execute(AlertRequest alertRequest) {

        Alert alert = new Alert();
        alert.setResultado(alertRequest.getResultado());
        alert.setDescripcion(alertRequest.getDescripcion());
        alert.setEstado(
                alertRequest.getEstado() != null
                        ? alertRequest.getEstado()
                        : Alert.AlertStatus.PENDIENTE
        );
        alert.setStockActual(alertRequest.getStockActual());
        alert.setStockMinimo(alertRequest.getStockMinimo());

        if (alertRequest.getProductId() != null) {
            Product product = productService.getById(alertRequest.getProductId());
            alert.setProduct(product);
        }

        return alertService.save(alert);
    }
}
