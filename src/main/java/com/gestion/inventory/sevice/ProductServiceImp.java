package com.gestion.inventory.sevice;

import com.gestion.inventory.domain.Product;
import com.gestion.inventory.repository.ProductRepository;

import java.util.List;

import org.springframework.stereotype.Service;
@Service
public class ProductServiceImp implements IProductService {
    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }
}
