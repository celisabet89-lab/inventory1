package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/** Historial de lo que le pasa a cada ONU: asignación, recojo y diagnóstico. */
@Getter
@Setter
@Entity
@Table(name = "onu_movimiento")
public class OnuMovimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** REGISTRO | ASIGNACION | RECOJO | DIAGNOSTICO */
    @Column(length = 20)
    private String tipo;

    private LocalDateTime fecha;

    private String estadoAnterior;
    private String estadoNuevo;

    /** Usuario que recibe (asignación/recojo) o que diagnostica. */
    private String usuario;

    /** Cargo de quien realizó la operación (Técnico, Administrador, Encargado...). */
    private String cargo;

    /** Almacén de origen (asignación) o de destino (diagnóstico). */
    private String almacen;

    @Column(columnDefinition = "TEXT")
    private String detalle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "onu_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Onu onu;

    @PrePersist
    protected void onCreate() {
        if (fecha == null) fecha = LocalDateTime.now();
    }
}