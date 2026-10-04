package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    private String nombre;
    private String apellido;
    private String gmail;
    private Integer telefono;
    private String direccion;
    private String ci;
    private String zona;
    private String estado;
    private String observaciones;
    private LocalDate fechaRegistro;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "lugarVenta_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "cliente"})
    private LugarVenta  lugarVenta;
    @PrePersist
    protected void onCreate() {
        if (fechaRegistro == null) fechaRegistro = LocalDate.now();
        if (estado == null) estado = "ACTIVO";
    }
}