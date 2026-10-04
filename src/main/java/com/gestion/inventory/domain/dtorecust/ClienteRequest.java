package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ClienteRequest {
    String nombre;
    String gmail;
    String apellido;
    Integer telefono;
    String direccion;
    String ci;
    String zona;
    String estado;
    String observaciones;
    LocalDate fechaRegistro;
    Long idLugarVenta;
}