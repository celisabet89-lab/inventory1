package com.gestion.inventory.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.inventory.domain.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
}
