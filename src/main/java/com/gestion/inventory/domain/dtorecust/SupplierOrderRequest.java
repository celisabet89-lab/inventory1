package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class SupplierOrderRequest {
    private Long   alertId;
    private String supplierEmail;
    private String supplierName;
    private String productoNombre;
    private Integer cantidadPedida;
    private String  observaciones;
    private String  solicitadoPor;
}