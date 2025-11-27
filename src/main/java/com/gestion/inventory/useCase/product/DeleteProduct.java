package com.gestion.inventory.useCase.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.sevice.IProductService;

@Service
public class DeleteProduct {
    @Autowired
    IProductService productService;
    public void execute(Long idProduct) {
        productService.deleteById(idProduct);
    }
}
