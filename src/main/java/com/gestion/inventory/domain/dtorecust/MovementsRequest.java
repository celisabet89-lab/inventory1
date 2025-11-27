package com.gestion.inventory.domain.dtorecust;

import java.sql.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class MovementsRequest {
    Integer cantidad;
    Date fecha;
    String tipo;
    String responsable;
    String observaciones;
}
