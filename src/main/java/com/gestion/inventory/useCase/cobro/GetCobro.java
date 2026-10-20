
package com.gestion.inventory.useCase.cobro;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.sevice.ICobroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GetCobro {
    @Autowired
    ICobroService cobroService;
    public GetCobro(ICobroService cobroService) {
        this.cobroService = cobroService;
    }
    public Cobro execute(Long id) {
        return cobroService.getById(id);
    }
}
