package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ProductRequest {
    String articulo;
    String descripcion;
    Integer unidad;
    String foto;
    BigDecimal precio;
    String categoria;

}
