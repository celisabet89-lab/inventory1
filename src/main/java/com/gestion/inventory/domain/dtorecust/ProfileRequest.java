package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProfileRequest {
    String name;
    String lastName;
    Long telefono;
    Long ci;
    String cargo;
}
