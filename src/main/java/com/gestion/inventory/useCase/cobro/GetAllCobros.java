
package com.gestion.inventory.useCase.cobro;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.sevice.ICobroService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetAllCobros {
    ICobroService cobroService;
    public GetAllCobros(ICobroService cobroService) {
        this.cobroService = cobroService;
    }
    public List<Cobro> execute() {
        return cobroService.getAll();
    }
}
