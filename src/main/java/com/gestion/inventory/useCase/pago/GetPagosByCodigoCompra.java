package com.gestion.inventory.useCase.pago;

import com.gestion.inventory.domain.Pago;
import com.gestion.inventory.sevice.IPagoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPagosByCodigoCompra {
    @Autowired
    IPagoService pagoService;

            public GetPagosByCodigoCompra(IPagoService pagoService) {
                this.pagoService = pagoService;
            }

            public List<Pago> execute(String codigoCompra) {
                return pagoService.getByCodigoCompra(codigoCompra);
            }
}