package com.gestion.inventory.controller;

import java.util.List;

import com.gestion.inventory.domain.dtorecust.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gestion.inventory.domain.Product;
import com.gestion.inventory.useCase.product.CreateProduct;
import com.gestion.inventory.useCase.product.DeleteProduct;
import com.gestion.inventory.useCase.product.GetAllProduct;
import com.gestion.inventory.useCase.product.GetProduct;
import com.gestion.inventory.useCase.product.UpdateProduc;

@RestController
@RequestMapping("/api/product")
public class ProducController {
    @Autowired
    CreateProduct createProduct;
    @Autowired
    DeleteProduct deleteProduct;
    @Autowired
    GetProduct getProduct;
    @Autowired
    GetAllProduct getAllProduct;
    @Autowired
    UpdateProduc updateProduc;
    
    @GetMapping
    public List<Product> getAll() {
        return getAllProduct.execute();
    }

    @GetMapping("/{idProducto}")
    public Product getById(@PathVariable Long idProducto) {
        return getProduct.execute(idProducto);
    }

    @PostMapping
    public Product save(@RequestBody ProductRequest productRequest) {
        return createProduct.execute(productRequest);
    }

    @PutMapping("/{idProducto}")
    public Product update(@PathVariable Long idProducto, @RequestBody ProductRequest productRequest) {
        return updateProduc.execute(idProducto, productRequest);
    }

    @DeleteMapping("/{idProducto}")
    public void deleteById(@PathVariable Long idProducto) {
        deleteProduct.execute(idProducto);
    }
}
