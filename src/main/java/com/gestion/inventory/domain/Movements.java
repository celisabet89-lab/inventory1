package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "movements")
public class Movements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMovements;
    Integer cantidad;
    LocalDateTime fecha;
    String tipo;
    String responsable;
    @Column(columnDefinition = "TEXT")
    String observaciones;
    String codigo;
    String formaPago;
    String estadoPago;
    @Column(length = 20)
    String estadoMovimiento;
    BigDecimal total;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler",
            "password"
    })
    User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({
            "hibernateLazyInitializer",
            "handler"
    })
    Product product;
    @PrePersist
    protected void onCreate() {
        if (this.fecha == null) {
            this.fecha = LocalDateTime.now();
        }

        if (
                this.estadoMovimiento == null ||
                        this.estadoMovimiento.trim().isEmpty()
        ) {
            this.estadoMovimiento = "ACTIVO";
        }
    }
}