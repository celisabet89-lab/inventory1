package com.gestion.inventory.useCase.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Product;
import com.gestion.inventory.sevice.IProductService;

@Service
public class GetAllProduct {
    @Autowired
    IProductService productService;
    public List<Product> execute(){
        return productService.getAll();
    }
}
