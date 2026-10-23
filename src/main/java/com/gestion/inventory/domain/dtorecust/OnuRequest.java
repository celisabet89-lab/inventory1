package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OnuRequest {
    String marca;
    String modelo;
    String serial;
    String mac;
    String observaciones;
    String estado;
    Long contrato;
    String codigoQR;
    String tipoPortador;
    String portador;
    String almacen;
    String registradoPor;
    String cargoRegistro;
}