package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    @ManyToOne(fetch = FetchType.EAGER)  // FIX: EAGER para que cargue el producto
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Product product;
}