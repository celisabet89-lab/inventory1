package com.gestion.inventory.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter @Setter
@Entity
@Table(name = "precio_venta")
public class PrecioVenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double precioCompra;
    private Double precioVenta;
    private Double margenGanancia;  // porcentaje
    private LocalDate fechaVigencia;
    private String  observacion;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Product product;

    @PrePersist
    protected void onCreate() {
        if (fechaVigencia == null) fechaVigencia = LocalDate.now();
        if (precioCompra != null && precioVenta != null && precioCompra > 0)
            margenGanancia = ((precioVenta - precioCompra) / precioCompra) * 100;
    }
}