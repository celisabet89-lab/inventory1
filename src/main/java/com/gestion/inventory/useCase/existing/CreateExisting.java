package com.gestion.inventory.useCase.existing;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.Product;
import com.gestion.inventory.domain.dtorecust.ExistingRequest;
import com.gestion.inventory.sevice.IExistingService;
import com.gestion.inventory.sevice.IProductService;
import com.gestion.inventory.sevice.StockSyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateExisting {
    @Autowired IExistingService existingService;
    @Autowired IProductService  productService;
    @Autowired StockSyncService stockSync;

    @org.springframework.transaction.annotation.Transactional
    public Existing execute(ExistingRequest r) {
        Existing existing = new Existing();
        existing.setStockACtual(r.getStockACtual() != null ? r.getStockACtual() : 0);
        existing.setStockMinimo(r.getStockMinimo() != null ? r.getStockMinimo() : 5);

        if (r.getProductId() != null) {
            Product product = productService.getById(r.getProductId());
            existing.setProduct(product);
        }
        Existing guardado = existingService.save(existing);
        stockSync.desdeStock(guardado);
        return guardado;
    }
}