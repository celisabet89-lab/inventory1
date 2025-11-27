package com.gestion.inventory.useCase.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Product;
import com.gestion.inventory.sevice.IProductService;

@Service
public class GetProduct {
    @Autowired
    IProductService productService;
    public Product execute(Long id){
        return productService.getById(id);
    }
}
