package com.gestion.inventory.domain.dtorecust;
import com.gestion.inventory.domain.Alert;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlertRequest {

    String resultado;
    String descripcion;
    Alert.AlertStatus estado;
    Integer stockActual;
    Integer stockMinimo;
    Long productId;
    Long clienteId;
    Long onuId;
}
