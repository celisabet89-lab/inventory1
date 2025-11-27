package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDate;


@Getter
@Setter
@Entity
public class Alert {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAlert;

    String resultado;
    String descripcion;
    Date fecha;
    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "onuId")
    private Onu onu;

    @PrePersist
    protected void onCreate() {
        this.fecha = java.sql.Date.valueOf(LocalDate.now());}
}
