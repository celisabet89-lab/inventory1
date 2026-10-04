package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProveedorRequest {
    private String empresa;
    private String pais;
    private String contacto;
    private String email;
    String ciNit;
    private String direccion;
    private String estado;
    private String observaciones;
    private Long profileId;
}