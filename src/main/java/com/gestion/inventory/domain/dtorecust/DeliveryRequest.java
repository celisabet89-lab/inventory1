package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

@Getter @Setter
public class DeliveryRequest {
    private Long   clienteId;
    private Long   tecnicoId;
    private String estado;
    private LocalDate fecha;
    private String observacion;
    private String direccionEntrega;
    private List<DeliveryDetailsRequest> detalles;
}