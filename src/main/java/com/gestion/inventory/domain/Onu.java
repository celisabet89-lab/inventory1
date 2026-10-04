package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
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
    String codigoQR;
    String tipoPortador;
    String portador;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}