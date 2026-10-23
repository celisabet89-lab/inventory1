package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class OnuOperacionRequest {

    LocalDate fecha;
    String usuario;
    String tipoDestino;
    String cargo;
    String empresa;
    Long clienteId;
    Long contrato;
    String observaciones;
    List<Item> items;
    Long onuId;
    String estadoNuevo;
    String diagnostico;
    String almacen;
    @Getter
    @Setter
    public static class Item {
        Long onuId;
        String observaciones;
    }
}