package com.gestion.inventory.useCase.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gestion.inventory.domain.Product;
import com.gestion.inventory.domain.dtorecust.ProductRequest;
import com.gestion.inventory.sevice.IProductService;
@Service
public class UpdateProduc {
    @Autowired
    IProductService productService;

    public Product execute(Long id, ProductRequest productRequest){
        Product productUpdete =productService.getById(id);
        productUpdete.setArticulo(productRequest.getArticulo());
        productUpdete.setDescripcion(productRequest.getDescripcion());
        productUpdete.setUnidad(productRequest.getUnidad());
        productUpdete.setFoto(productRequest.getFoto());
        productUpdete.setPrecio(productRequest.getPrecio());
        productUpdete.setCategoria(productRequest.getCategoria());
        return productService.save(productUpdete);
    }
}
