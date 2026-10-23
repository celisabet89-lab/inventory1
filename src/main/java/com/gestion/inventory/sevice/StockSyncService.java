package com.gestion.inventory.sevice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.inventory.domain.Alert;
import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.repository.AlertRepository;
import com.gestion.inventory.repository.ExistingRepository;
import com.gestion.inventory.repository.ProductRepository;

@Service
public class StockSyncService {

    private static final int MINIMO_POR_DEFECTO = 5;

    @Autowired ExistingRepository existingRepository;
    @Autowired ProductRepository productRepository;
    @Autowired AlertRepository alertRepository;

    @Transactional
    public Existing desdeProducto(Product producto) {
        if (producto == null || producto.getIdProducto() == null) return null;
        Existing stock = existingRepository.findByProduct(producto).orElseGet(() -> {
            Existing nuevo = new Existing();
            nuevo.setProduct(producto);
            nuevo.setStockMinimo(MINIMO_POR_DEFECTO);
            return nuevo;
        });
        stock.setStockACtual(producto.getUnidad() == null ? 0 : producto.getUnidad());
        if (stock.getStockMinimo() == null) stock.setStockMinimo(MINIMO_POR_DEFECTO);
        stock = existingRepository.save(stock);
        resolverAlertas(stock);
        return stock;
    }


    @Transactional
    public void desdeStock(Existing stock) {
        if (stock == null) return;
        Product p = stock.getProduct();
        if (p != null && stock.getStockACtual() != null) {
            p.setUnidad(stock.getStockACtual());
            productRepository.save(p);
        }
        resolverAlertas(stock);
    }

    @Transactional
    public void resolverAlertas(Existing stock) {
        if (stock == null || stock.getProduct() == null) return;
        int actual = stock.getStockACtual() == null ? 0 : stock.getStockACtual();
        int minimo = stock.getStockMinimo() == null ? 0 : stock.getStockMinimo();
        if (actual <= minimo) return;

        List<Alert> pendientes = alertRepository.findByProductAndEstado(stock.getProduct(), Alert.AlertStatus.PENDIENTE);
        for (Alert a : pendientes) {
            a.setEstado(Alert.AlertStatus.GESTIONADA);
            a.setStockActual(actual);
        }
        if (!pendientes.isEmpty()) alertRepository.saveAll(pendientes);
    }
}