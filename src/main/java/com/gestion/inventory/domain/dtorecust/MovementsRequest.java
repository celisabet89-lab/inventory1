package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;

@Getter @Setter
public class MovementsRequest {
    Long idProducto;
    Integer cantidad;
    Date fecha;
    String tipo;
    String responsable;
    String observaciones;
 }