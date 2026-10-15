
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
    @PrePersist
    protected void onCreate() {
        if (fecha == null) {
            fecha = LocalDateTime.now();
        }
    }
}
