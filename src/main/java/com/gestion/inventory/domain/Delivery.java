package com.gestion.inventory.domain;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDelivery;
    Date fecha;
    String observacion;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;
    @PrePersist
    protected void onCreate() {
        this.fecha = Date.valueOf(LocalDate.now());}
}
