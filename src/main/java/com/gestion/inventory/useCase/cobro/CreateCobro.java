
package com.gestion.inventory.useCase.cobro;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.domain.dtorecust.CobroRequest;
import com.gestion.inventory.sevice.ICobroService;
import org.springframework.stereotype.Component;

@Component
public class CreateCobro {
    ICobroService cobroService;
    public CreateCobro(ICobroService cobroService) {
        this.cobroService = cobroService;
    }
    public Cobro execute(CobroRequest request) {
        return cobroService.registrarCobro(request);
    }
}