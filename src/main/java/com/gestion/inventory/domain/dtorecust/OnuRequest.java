package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OnuRequest {
    String marca;
    String modelo;
    String serial;
    String mac;
    String observaciones;
    String estado;
    Long contrato;
}
