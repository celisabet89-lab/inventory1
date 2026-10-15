
package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class CobroRequest {
    String codigoVenta;
    BigDecimal monto;
    String formaPago;
    String observaciones;
}
