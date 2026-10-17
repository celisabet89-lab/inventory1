package com.gestion.inventory.domain.dtorecust;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
@Getter
@Setter
public class PagoRequest {
    String codigoCompra;
    BigDecimal monto;
    String formaPago;
    String observaciones;
}
