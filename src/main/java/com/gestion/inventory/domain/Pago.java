package com.gestion.inventory.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

        import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "pagos")
public class Pago {

            @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPago;
    String codigoCompra;
    BigDecimal monto;
    LocalDateTime fecha;
    String formaPago;
    String observaciones;
    String proveedorNombre;
            @Column(length = 20)
    String estado;
    @Column(columnDefinition = "TEXT")
    String motivoAnulacion;
    LocalDateTime fechaAnulacion;

            @PrePersist
    protected void onCreate() {
                if (fecha == null) {
                        fecha = LocalDateTime.now();
                    }
                if (estado == null || estado.trim().isEmpty()) {
                        estado = "ACTIVO";
                    }
            }
}

