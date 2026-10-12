package com.gestion.inventory.useCase.Alert;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.repository.ExistingRepository;
import com.gestion.inventory.sevice.IAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CheckStockAlerts {

    @Autowired private ExistingRepository existingRepository;
    @Autowired private IAlertService alertService;

    @Transactional
    public List<Alert> execute() {
        List<Existing> todos = existingRepository.findAll();
        List<Alert> nuevasAlertas = new ArrayList<>();

        for (Existing stock : todos) {
            if (stock.getProduct() == null) continue;

            int actual  = stock.getStockACtual() != null ? stock.getStockACtual() : 0;
            int minimo  = stock.getStockMinimo()  != null ? stock.getStockMinimo()  : 0;

            // Solo generar alerta si stock <= mínimo
            if (actual <= minimo) {
                // Verificar si ya existe alerta PENDIENTE para este producto
                boolean yaExiste = alertService.getAll().stream()
                        .anyMatch(a -> a.getProduct() != null
                                && a.getProduct().getIdProducto().equals(stock.getProduct().getIdProducto())
                                && a.getEstado() == Alert.AlertStatus.PENDIENTE);

                if (!yaExiste) {
                    Alert alerta = new Alert();
                    alerta.setProduct(stock.getProduct());
                    alerta.setStockActual(actual);
                    alerta.setStockMinimo(minimo);
                    alerta.setEstado(Alert.AlertStatus.PENDIENTE);
                    alerta.setFecha(LocalDateTime.now());

                    if (actual == 0) {
                        alerta.setResultado("CRÍTICO");
                        alerta.setDescripcion("⚠️ Sin stock: " + stock.getProduct().getArticulo()
                                + " — Stock: 0 / Mínimo: " + minimo);
                    } else {
                        int porcentaje = Math.round((float) actual / minimo * 100);
                        alerta.setResultado("STOCK BAJO");
                        alerta.setDescripcion("Stock bajo: " + stock.getProduct().getArticulo()
                                + " — " + actual + "/" + minimo + " (" + porcentaje + "% del mínimo)");
                    }

                    nuevasAlertas.add(alertService.save(alerta));
                }
            }
        }
        return nuevasAlertas;
    }
}