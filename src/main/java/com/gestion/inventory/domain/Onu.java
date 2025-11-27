package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Onu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String marca;
    String modelo;
    String serial;
    String mac;
    String observaciones;
    String estado;
    Long contrato;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
