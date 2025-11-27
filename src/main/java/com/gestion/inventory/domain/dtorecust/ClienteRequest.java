package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ClienteRequest {
    String name;
    String apellido;
    Integer telefono;
    String direccion;
}
