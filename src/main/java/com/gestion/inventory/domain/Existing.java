package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Existing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idExisting;
    Integer stockACtual;
    Integer stockMinimo;
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
