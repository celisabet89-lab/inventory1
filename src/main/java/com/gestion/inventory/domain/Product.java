package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idProducto;
    String articulo;
    @Column(columnDefinition = "TEXT")
    String descripcion;
    Integer unidad;
    @Column(columnDefinition = "LONGTEXT")
    String foto;
    BigDecimal precio;
    String codigoBarras;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "categoria_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "productos"})
    private Categoria categoria;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unidad_medida_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "productos"})
    private UnidadMedida unidadMedida;

}

    