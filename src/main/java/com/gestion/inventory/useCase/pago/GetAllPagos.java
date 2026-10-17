package com.gestion.inventory.useCase.pago;

import com.gestion.inventory.domain.Pago;
import com.gestion.inventory.sevice.IPagoService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllPagos {
    IPagoService pagoService;

    public GetAllPagos(IPagoService pagoService) {
        this.pagoService = pagoService;
    }

    public List<Pago> execute() {
        return pagoService.getAll();
    }
}
