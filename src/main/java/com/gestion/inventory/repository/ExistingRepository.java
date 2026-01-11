package com.gestion.inventory.repository;

import com.gestion.inventory.domain.Existing;
import com.gestion.inventory.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ExistingRepository extends JpaRepository<Existing, Long> {
  Optional<Existing> findByProduct(Product product);

}
