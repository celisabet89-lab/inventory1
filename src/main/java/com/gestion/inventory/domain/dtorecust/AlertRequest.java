package com.gestion.inventory.domain.dtorecust;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertRequest {
    String resultado;
    String descripcion;
    Date fecha;
}
