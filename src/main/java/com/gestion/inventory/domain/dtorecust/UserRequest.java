package com.gestion.inventory.domain.dtorecust;
import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class UserRequest {
    String email;
    String password;
    String name;
    String lastName;
    Long telefono;
    Long ci;
    String cargo;
    Long rolId;
}