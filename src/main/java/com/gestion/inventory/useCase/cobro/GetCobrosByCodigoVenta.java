
package com.gestion.inventory.useCase.cobro;

import com.gestion.inventory.domain.Cobro;
import com.gestion.inventory.sevice.ICobroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCobrosByCodigoVenta {
    @Autowired
    ICobroService cobroService;
    public GetCobrosByCodigoVenta(ICobroService cobroService) {
        this.cobroService = cobroService;
    }
    public List<Cobro> execute(String codigoVenta) {
        return cobroService.getByCodigoVenta(codigoVenta);
    }
}
