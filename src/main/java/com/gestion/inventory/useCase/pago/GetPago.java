package com.gestion.inventory.useCase.pago;

import com.gestion.inventory.domain.Pago;
import com.gestion.inventory.sevice.IPagoService;
import org.springframework.stereotype.Component;

@Component
public class GetPago {
    IPagoService pagoService;

    public GetPago(IPagoService pagoService) {
        this.pagoService = pagoService;
    }

    public Pago execute(Long id) {
        return pagoService.getById(id);
    }
}
