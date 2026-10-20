package com.gestion.inventory.useCase.pago;


import com.gestion.inventory.domain.Pago;
import com.gestion.inventory.domain.dtorecust.PagoRequest;
import com.gestion.inventory.sevice.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreatePago {
    @Autowired
    IPagoService pagoService;

            public CreatePago(IPagoService pagoService) {
                this.pagoService = pagoService;
            }

            public Pago execute(PagoRequest request) {
                return pagoService.registrarPago(request);
            }
}
