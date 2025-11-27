package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class DeliveryDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDeliveryDetails;
    Integer cantidad;
    @ManyToOne
    @JoinColumn(name = "deliveryId")
    private Delivery delivery;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
