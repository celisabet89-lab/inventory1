package com.gestion.inventory.sevice;

import java.util.List;

import com.gestion.inventory.domain.Product;

public interface IProductService {
    List<Product> getAll();
    Product getById(Long id);
    void deleteById(Long id);
    Product save(Product product);
}
