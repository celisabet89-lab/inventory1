package com.gestion.inventory.domain.dtorecust;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeliveryDetailsRequest {
    private Long    productId;
    private Integer cantidad;
}