package com.gestion.inventory.repository;

import com.gestion.inventory.domain.DeliveryDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryDetailsRepository extends JpaRepository<DeliveryDetails, Long> {
}
