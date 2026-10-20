package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "onu_movimiento")
public class OnuMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(length = 20)
    String tipo;
    LocalDateTime fecha;
    String estadoAnterior;
    String estadoNuevo;
    String usuario;
    String almacen;
    @Column(columnDefinition = "TEXT")
    String detalle;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "onu_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    Onu onu;
    @PrePersist
    protected void onCreate() {
        if (fecha == null) fecha = LocalDateTime.now();
    }
}