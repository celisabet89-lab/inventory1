package com.gestion.inventory.domain;

import java.sql.Date;
import java.time.LocalDate;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    Date fecha;
    String datos;
    @ManyToOne
    @JoinColumn(name = "onuId")
    private Onu onu;

    @ManyToOne
    @JoinColumn(name = "clienteId")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @PrePersist
    protected void onCreate() {
        this.fecha = Date.valueOf(LocalDate.now());}
}
