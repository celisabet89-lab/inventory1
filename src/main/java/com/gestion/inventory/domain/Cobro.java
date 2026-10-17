package com.gestion.inventory.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "cobros")
public class Cobro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idCobro;
    String codigoVenta;
    BigDecimal monto;
    LocalDateTime fecha;
    String formaPago;
    String observaciones;
    String clienteNombre;

    @Column(nullable = false, length = 20)
    String estado = "ACTIVO";

    @PrePersist
    protected void onCreate() {
        if (fecha == null) fecha = LocalDateTime.now();
        if (estado == null || estado.isBlank()) estado = "ACTIVO";
    }
}
