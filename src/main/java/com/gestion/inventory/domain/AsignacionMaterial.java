package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/** Material entregado a un técnico para sus instalaciones. */
@Getter
@Setter
@Entity
@Table(name = "asignacion_material")
public class AsignacionMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    /** Técnico que recibe el material. */
    private Long tecnicoId;
    private String tecnicoNombre;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "producto_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product producto;

    private Integer cantidad;
    private Integer cantidadUsada = 0;
    private Integer cantidadDevuelta = 0;

    /** ASIGNADO | PARCIAL | CERRADO */
    @Column(length = 15)
    private String estado;

    /** Trabajo o instalación para la que se entrega (cliente, zona...). */
    private String destino;

    /** Quién entregó el material y con qué cargo. */
    private String asignadoPor;
    private String cargoAsignador;

    private LocalDateTime fechaCierre;

    /** Confirmación de recepción por el técnico (null = aún no confirmó). */
    private LocalDateTime fechaConfirmacion;
    private String confirmadoPor;

    /** Bitácora de usos y devoluciones. */
    @Column(columnDefinition = "TEXT")
    private String bitacora;

    @PrePersist
    protected void onCreate() {
        if (fecha == null) fecha = LocalDateTime.now();
        if (cantidadUsada == null) cantidadUsada = 0;
        if (cantidadDevuelta == null) cantidadDevuelta = 0;
        if (estado == null) estado = "ASIGNADO";
    }

    /** Material que el técnico todavía tiene en su poder. */
    @Transient
    public int getPendiente() {
        return (cantidad == null ? 0 : cantidad)
                - (cantidadUsada == null ? 0 : cantidadUsada)
                - (cantidadDevuelta == null ? 0 : cantidadDevuelta);
    }
}