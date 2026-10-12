package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovementsRequest {
    Long idProducto;
    Integer cantidad;
    LocalDateTime fecha;
    String tipo;
    String responsable;
    String observaciones;
    String codigo;
    Long userId;
    Long usuario;
    String formaPago;
    String estadoPago;
    BigDecimal total;
}