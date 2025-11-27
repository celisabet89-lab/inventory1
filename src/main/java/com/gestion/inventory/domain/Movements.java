package com.gestion.inventory.domain;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Movements {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idMovements;
    Integer cantidad;
    Date fecha;
    String tipo;
    String responsable;
    String observaciones;
    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @PrePersist
    protected void onCreate() {
        this.fecha = Date.valueOf(LocalDate.now());}
}
