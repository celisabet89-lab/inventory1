package com.gestion.inventory.domain.dtorecust;

import com.gestion.inventory.domain.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

import jakarta.persistence.Column;

@Getter
@Setter
public class ProductRequest {

    String articulo;
    @Column(columnDefinition = "TEXT")
    String descripcion;
    Integer unidad;
    @Column(columnDefinition = "LONGTEXT")
    String foto;
    BigDecimal precio;
    String codigoBarras;
    Long categoria;
    Long unidadMedida;

}
