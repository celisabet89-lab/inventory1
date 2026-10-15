
package com.gestion.inventory.useCase.cobro;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.sevice.ICobroService;
import org.springframework.stereotype.Component;

@Component
public class GetCobro {
    ICobroService cobroService;
    public GetCobro(ICobroService cobroService) {
        this.cobroService = cobroService;
    }
    public Cobro execute(Long id) {
        return cobroService.getById(id);
    }
}
