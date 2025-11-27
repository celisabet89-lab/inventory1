package com.gestion.inventory.useCase.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Product;
import com.gestion.inventory.domain.dtorecust.ProductRequest;
import com.gestion.inventory.sevice.IProductService;

@Service
public class CreateProduct {
    @Autowired
    IProductService productService;

    public Product execute(ProductRequest productRequest){
        Product product =new Product();
        product.setArticulo(productRequest.getArticulo());
        product.setDescripcion(productRequest.getDescripcion());
        product.setUnidad(productRequest.getUnidad());
        product.setFoto(productRequest.getFoto());
        product.setPrecio(productRequest.getPrecio());
        product.setCategoria(productRequest.getCategoria());
        return productService.save(product);

        
    }
}
