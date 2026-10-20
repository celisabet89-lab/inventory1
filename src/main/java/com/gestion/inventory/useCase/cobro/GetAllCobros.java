
package com.gestion.inventory.useCase.cobro;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.sevice.ICobroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCobros {
    @Autowired
    ICobroService cobroService;
    public GetAllCobros(ICobroService cobroService) {
        this.cobroService = cobroService;
    }
    public List<Cobro> execute() {
        return cobroService.getAll();
    }
}
