package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.repository.AlertRepository;
import com.gestion.inventory.repository.ExistingRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
@Service
public class AlertSchedulerService {

    private final ExistingRepository existingRepository;
    private final AlertRepository alertRepository;
    private final EmailNotificationService emailService; // A implementar

    public AlertSchedulerService(
            ExistingRepository existingRepository,
            AlertRepository alertRepository,
            EmailNotificationService emailService) {
        this.existingRepository = existingRepository;
        this.alertRepository = alertRepository;
        this.emailService = emailService;
    }

    /**
     * Se ejecuta cada hora para verificar niveles de stock críticos
     * Cumple: "100% de pedidos gestionados en 24h"
     */
    @Scheduled(cron = "0 0 * * * *") // Cada hora
    @Transactional
    public void verificarStockCritico() {
        List<Existing> todosLosStocks = existingRepository.findAll();

        for (Existing stock : todosLosStocks) {
            verificarYCrearAlerta(stock);
        }
    }

    private void verificarYCrearAlerta(Existing stock) {
        // Calcular el 90% del stock mínimo
        double umbralCritico = stock.getStockMinimo() * 0.9;

        // Verificar si el stock actual está en nivel crítico
        if (stock.getStockACtual() <= umbralCritico) {
            Product producto = stock.getProduct();

            // Verificar si ya existe una alerta PENDIENTE para este producto
            boolean alertaExistente = alertRepository.existsByProductAndEstado(
                    producto,
                    Alert.AlertStatus.PENDIENTE
            );

            if (!alertaExistente) {
                crearAlertaAutomatica(stock, producto);
            }
        }
    }

    private void crearAlertaAutomatica(Existing stock, Product producto) {
        Alert nuevaAlerta = new Alert();
        nuevaAlerta.setProduct(producto);
        nuevaAlerta.setResultado("STOCK_CRITICO");
        nuevaAlerta.setDescripcion(
                String.format(
                        "Stock crítico detectado: %s tiene %d unidades (mínimo: %d). " +
                                "Se requiere reposición urgente.",
                        producto.getArticulo(),
                        stock.getStockACtual(),
                        stock.getStockMinimo()
                )
        );
        nuevaAlerta.setEstado(Alert.AlertStatus.PENDIENTE);
        nuevaAlerta.setStockActual(stock.getStockACtual());
        nuevaAlerta.setStockMinimo(stock.getStockMinimo());
        nuevaAlerta.setFecha(LocalDateTime.now());

        // Guardar la alerta
        Alert alertaGuardada = alertRepository.save(nuevaAlerta);

        // Enviar notificación por email enviarAlertaStockCritico
        emailService.enviarAlertaStockCritico(alertaGuardada);
    }

    /**
     * Método manual para verificación inmediata (útil para testing)
     */
    public void verificarStockInmediato() {
        verificarStockCritico();
    }
}